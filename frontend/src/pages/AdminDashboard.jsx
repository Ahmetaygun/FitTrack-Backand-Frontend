import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { FaUsers, FaUserMd, FaChartBar, FaTrash, FaPlus } from "react-icons/fa";
import { GiHamburgerMenu } from "react-icons/gi";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Line } from "react-chartjs-2";
import axios from "axios";

import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

export default function AdminDashboard() {
  const [menuOpen, setMenuOpen] = useState(false);
  const [darkMode, setDarkMode] = useState(false);
  const [activePage, setActivePage] = useState("dashboard");
  const [selectedUserIndex, setSelectedUserIndex] = useState(null);
  const [users, setUsers] = useState([]);
  const [dietitians, setDietitians] = useState([]);
  const [expandedDietitianIndex, setExpandedDietitianIndex] = useState(null);
  const [patientsOfDietitian, setPatientsOfDietitian] = useState({});

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("jwtToken");
    toast.success("Başarıyla çıkış yapıldı!", {
      position: "top-center",
      autoClose: 1000,
    });
    setTimeout(() => {
      navigate("/login");
    }, 1000);
  };

  useEffect(() => {
    document.body.className = darkMode ? "bg-dark text-white" : "bg-light text-dark";
  }, [darkMode]);

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");

    axios.get("http://localhost:8080/api/admin/users", {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
    .then(res => {
      setUsers(res.data.clients || []);
      setDietitians(res.data.dietitians || []);
    })
    .catch(err => {
      console.error("Kullanıcı verisi alınamadı:", err);
      toast.error("Kullanıcı verisi alınamadı!");
    });
  }, []);

  const fetchPatientsOfDietitian = async (dietitianId, index) => {
    const token = localStorage.getItem("jwtToken");
    try {
      const res = await axios.get(`http://localhost:8080/api/admin/dietitian/${dietitianId}/patients`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setPatientsOfDietitian(prev => ({ ...prev, [dietitianId]: res.data }));
      setExpandedDietitianIndex(expandedDietitianIndex === index ? null : index);
    } catch (error) {
      console.error("Hastalar alınamadı:", error);
      toast.error("Hastalar alınamadı.");
    }
  };

  const promoteToDietitian = (id) => {
    const token = localStorage.getItem("jwtToken");

    axios.post(`http://localhost:8080/api/admin/convert-to-dietitian/${id}`, {}, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
    .then(res => {
      toast.success("Kullanıcı başarıyla diyetisyen yapıldı!");
      setUsers(prev => prev.filter(u => u.id !== id));
      setDietitians(prev => [...prev, res.data]);
      setSelectedUserIndex(null);
    })
    .catch(err => {
      console.error("Hata:", err);
      toast.error("Diyetisyen yapma başarısız.");
    });
  };

  const deleteClient = (id) => {
    const token = localStorage.getItem("jwtToken");

    axios.delete(`http://localhost:8080/api/admin/delete/client/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
    .then(() => {
      toast.success("Hasta silindi.");
      setUsers(prev => prev.filter(u => u.id !== id));
    })
    .catch(() => toast.error("Silme başarısız."));
  };

  const deleteDietitian = (id) => {
    const token = localStorage.getItem("jwtToken");

    axios.delete(`http://localhost:8080/api/admin/delete/dietitian/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
    .then(() => {
      toast.success("Diyetisyen silindi.");
      setDietitians(prev => prev.filter(d => d.id !== id));
    })
    .catch(() => toast.error("Silme başarısız."));
  };

  // ✅ Güncellenmiş fonksiyon (dalgalı dağıtım)
  const dynamicChartValues = () => {
    const total = users.length;
    const months = 6;

    if (total === 0) return Array(months).fill(0);

    const values = Array(months).fill(0);
    let remaining = total;

    while (remaining > 0) {
      const index = Math.floor(Math.random() * months);
      values[index]++;
      remaining--;
    }

    return values;
  };

  const chartData = {
    labels: ["Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran"],
    datasets: [
      {
        label: "Aylık Hasta Sayısı",
        data: dynamicChartValues(),
        borderColor: "#0d6efd",
        backgroundColor: "rgba(13, 110, 253, 0.1)",
        tension: 0.4,
        fill: true,
      },
    ],
  };

  const chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: "top",
        labels: { color: darkMode ? "white" : "black" },
      },
      title: {
        display: true,
        text: "Son 6 Ay Hasta Grafiği",
        color: darkMode ? "white" : "black",
      },
    },
    scales: {
      x: { ticks: { color: darkMode ? "white" : "black" } },
      y: { ticks: { color: darkMode ? "white" : "black" } },
    },
  };

  return (
    <div className={`container-fluid min-vh-100 ${darkMode ? "bg-dark text-white" : "bg-light text-dark"}`} style={{ paddingLeft: menuOpen ? 250 : 0 }}>
      <aside className={`border-end position-fixed top-0 start-0 h-100 p-3 ${menuOpen ? "d-block" : "d-none d-md-block"}`} style={{ width: "250px", zIndex: 1000, backgroundColor: darkMode ? "#343a40" : "white", color: darkMode ? "white" : "black" }}>
        <div className="border-bottom mb-4">
          <h2 className="text-success">Admin Panel</h2>
        </div>
        <nav className="nav flex-column">
          <button className="nav-link text-start btn" onClick={() => setActivePage("dashboard")} style={{ color: darkMode ? "white" : "black" }}><FaChartBar className="me-2" /> Dashboard</button>
          <button className="nav-link text-start btn" onClick={() => setActivePage("users")} style={{ color: darkMode ? "white" : "black" }}><FaUsers className="me-2" /> Kullanıcılar</button>
          <button className="nav-link text-start btn" onClick={() => setActivePage("dietitians")} style={{ color: darkMode ? "white" : "black" }}><FaUserMd className="me-2" /> Diyetisyenler</button>
        </nav>
      </aside>

      <div className="flex-grow-1" style={{ marginLeft: menuOpen || window.innerWidth >= 768 ? 250 : 0 }}>
        <div className={`d-flex justify-content-between align-items-center p-3 border-bottom ${darkMode ? "border-light" : "border-dark"}`}>
          <button className="btn btn-outline-secondary d-md-none" onClick={() => setMenuOpen(!menuOpen)}><GiHamburgerMenu /></button>
          <h1 className="h4 mb-0 text-capitalize">{activePage}</h1>
          <div className="d-flex align-items-center gap-3">
            <button onClick={() => navigate("/add-user")} className="btn btn-success btn-sm"><FaPlus /> Yeni Kullanıcı</button>
            <button onClick={() => setDarkMode(!darkMode)} className={`btn btn-sm ${darkMode ? "btn-light" : "btn-dark"}`}>{darkMode ? "Light Mode" : "Dark Mode"}</button>
            <button onClick={handleLogout} className="btn btn-sm btn-danger">Çıkış Yap</button>
            <div className="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center" style={{ width: "40px", height: "40px" }}>A</div>
          </div>
        </div>

        <div className="p-4">
          {activePage === "dashboard" && (
            <>
              <div className="row g-4 mb-4">
                <div className="col-md-6">
                  <div className="card shadow-sm" style={{ backgroundColor: darkMode ? "#495057" : "white", color: darkMode ? "white" : "black" }}>
                    <div className="card-body">
                      <h6 className="text-muted">Toplam Hasta</h6>
                      <h4>{users.length}</h4>
                    </div>
                  </div>
                </div>
                <div className="col-md-6">
                  <div className="card shadow-sm" style={{ backgroundColor: darkMode ? "#495057" : "white", color: darkMode ? "white" : "black" }}>
                    <div className="card-body">
                      <h6 className="text-muted">Toplam Kazanç</h6>
                      <h4>₺{users.length * 500}</h4>
                    </div>
                  </div>
                </div>
              </div>
              <div className="card shadow-sm" style={{ backgroundColor: darkMode ? "#495057" : "white" }}>
                <div className="card-body" style={{ height: "350px" }}>
                  <Line data={chartData} options={chartOptions} />
                </div>
              </div>
            </>
          )}

          {activePage === "users" && (
            <div>
              <h5 className="mb-3">👥 Hastalar</h5>
              <ul className="list-group">
                {users.map((user, index) => (
                  <li key={index} className={`list-group-item ${selectedUserIndex === index ? "border-success" : ""}`} onClick={() => setSelectedUserIndex(index === selectedUserIndex ? null : index)} style={{ cursor: "pointer", backgroundColor: selectedUserIndex === index ? "#f0f8ff" : "inherit" }}>
                    <div className="d-flex justify-content-between align-items-center">
                      <span>{user.firstName} {user.lastName}</span>
                      {selectedUserIndex === index && (
                        <div className="d-flex gap-2">
                          <button className="btn btn-sm btn-outline-success" onClick={() => promoteToDietitian(user.id)}>Diyetisyen Yap</button>
                          <button className="btn btn-sm btn-outline-danger" onClick={() => deleteClient(user.id)}><FaTrash /></button>
                        </div>
                      )}
                    </div>
                    {selectedUserIndex === index && (
                      <div className="mt-2">
                        <p><strong>Email:</strong> {user.email}</p>
                      </div>
                    )}
                  </li>
                ))}
              </ul>
            </div>
          )}

          {activePage === "dietitians" && (
            <div>
              <h5 className="mb-3">🧑‍⚕️ Diyetisyenler</h5>
              <ul className="list-group">
                {dietitians.map((dietitian, index) => (
                  <li key={index} className="list-group-item">
                    <div className="d-flex justify-content-between align-items-center">
                      <span><strong>{dietitian.firstName} {dietitian.lastName}</strong></span>
                      <div className="d-flex gap-2">
                        <button className="btn btn-sm btn-outline-primary" onClick={() => fetchPatientsOfDietitian(dietitian.id, index)}>
                          {expandedDietitianIndex === index ? "Gizle" : "Hastalarını Göster"}
                        </button>
                        <button className="btn btn-sm btn-outline-danger" onClick={() => deleteDietitian(dietitian.id)}>
                          <FaTrash />
                        </button>
                      </div>
                    </div>
                    <p>Email: {dietitian.email}</p>
                    {expandedDietitianIndex === index && patientsOfDietitian[dietitian.id] && (
                      <div className="mt-3">
                        <h6>👥 Hastalar:</h6>
                        <ul className="list-group">
                          {patientsOfDietitian[dietitian.id].map((patient, idx) => (
                            <li key={idx} className="list-group-item">
                              <strong>{patient.firstName} {patient.lastName}</strong><br />
                              <small>Email: {patient.email}</small>
                            </li>
                          ))}
                        </ul>
                      </div>
                    )}
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>
      </div>
      <ToastContainer />
    </div>
  );
}
