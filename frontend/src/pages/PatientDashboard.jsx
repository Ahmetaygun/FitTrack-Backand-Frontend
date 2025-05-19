import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { FaHome, FaClipboardList, FaHeartbeat } from "react-icons/fa";
import { GiHamburgerMenu } from "react-icons/gi";
import { ToastContainer, toast } from "react-toastify";
import axios from "axios";
import "react-toastify/dist/ReactToastify.css";
import DietitianSelection from "../components/DietitianSelect";
import DietTypeSelect from "../components/DietTypeSelect";
import HealthInfoPage from "../pages/HealthInfoPage";

export default function PatientDashboard() {
  const [menuOpen, setMenuOpen] = useState(false);
  const [activePage, setActivePage] = useState("home");
  const [dietitians, setDietitians] = useState([]);
  const [myDietitian, setMyDietitian] = useState(null);
  const [healthInfo, setHealthInfo] = useState(null);
  const [dietList, setDietList] = useState(null);
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);
  const [dietTypeName, setDietTypeName] = useState(null); // ✅ yeni eklendi
  const navigate = useNavigate();

  const isMobile = windowWidth < 768;
  const primaryColor = "#dff5e3";
  const darkPrimary = "#3CB371";
  const lightBackground = "#f8fdfb";

  useEffect(() => {
    const handleResize = () => setWindowWidth(window.innerWidth);
    window.addEventListener("resize", handleResize);
    fetchMyProfile();
    fetchDietitians();
    fetchDietList();
    fetchHealthInfo();
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  const fetchMyProfile = async () => {
    try {
      const token = localStorage.getItem("jwtToken");
      if (!token) return navigate("/login");

      const response = await axios.get("http://localhost:8080/api/clients/me", {
        headers: { Authorization: `Bearer ${token}` },
      });

      const data = response.data;

      // ✅ Güncel ad ve tip alınıyor
      if (data.dietitianName) {
        setMyDietitian(data.dietitianName);
      } else {
        setMyDietitian(null);
      }

      setDietTypeName(data.dietTypeName || null); // ✅ burada tutuluyor
      console.log("GELEN VERİ:", data);
    } catch (error) {
      toast.error("Profil bilgisi alınamadı.");
    }
  };

  const fetchHealthInfo = async () => {
    try {
      const token = localStorage.getItem("jwtToken");
      const response = await axios.get("http://localhost:8080/api/clients/health-info", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setHealthInfo(response.data);
    } catch (error) {
      console.error("Sağlık bilgileri alınamadı.");
    }
  };

  const fetchDietitians = async () => {
    try {
      const token = localStorage.getItem("jwtToken");
      const response = await axios.get("http://localhost:8080/api/dietitians", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setDietitians(response.data);
    } catch (error) {
      toast.error("Diyetisyen bilgisi alınamadı.");
    }
  };

  const fetchDietList = async () => {
    try {
      const token = localStorage.getItem("jwtToken");
      const response = await axios.get("http://localhost:8080/api/clients/diet-list", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setDietList(response.data);
    } catch (error) {
      toast.error("Diyet listesi alınamadı.");
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("jwtToken");
    toast.success("Çıkış yapıldı!", { autoClose: 1000 });
    setTimeout(() => navigate("/login"), 1000);
  };

  const handleSelectDietitian = async (dietitianId) => {
    try {
      const token = localStorage.getItem("jwtToken");
      await axios.post(
        "http://localhost:8080/api/clients/select-dietitian",
        { dietitianId },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const selected = dietitians.find((d) => d.id === dietitianId);
      if (selected) setMyDietitian(`${selected.firstName} ${selected.lastName}`);

      toast.success("Diyetisyen seçildi!");
    } catch (error) {
      toast.error("Diyetisyen seçilemedi!");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("jwtToken");
      await axios.post(
        "http://localhost:8080/api/clients/health-info",
        healthInfo,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      toast.success("Sağlık bilgileri kaydedildi!");
    } catch (error) {
      toast.error("Sağlık bilgileri kaydedilemedi!");
    }
  };

  return (
    <>
      <div className="min-vh-100 d-flex" style={{ backgroundColor: lightBackground }}>
        {(!isMobile || menuOpen) && (
          <div
            className="position-fixed top-0 start-0 h-100 p-3 border-end"
            style={{
              width: "250px",
              zIndex: 1000,
              backgroundColor: isMobile ? "white" : darkPrimary,
            }}
          >
            <h2 className={isMobile ? "text-success" : "text-white"}>Hasta Paneli</h2>
            <nav className="nav flex-column mt-4">
              <button
                className={`nav-link text-start d-flex align-items-center gap-2 ${activePage === "home" ? "fw-bold" : ""}`}
                onClick={() => {
                  setActivePage("home");
                  setMenuOpen(false);
                }}
              >
                <FaHome /> Ana Sayfa
              </button>

              <button
                className={`nav-link text-start d-flex align-items-center gap-2 ${activePage === "health" ? "fw-bold" : ""}`}
                onClick={() => {
                  setActivePage("health");
                  setMenuOpen(false);
                }}
              >
                <FaHeartbeat /> Sağlık Bilgilerim
              </button>

              <button
                className={`nav-link text-start d-flex align-items-center gap-2 ${activePage === "diet" ? "fw-bold" : ""}`}
                onClick={() => {
                  setActivePage("diet");
                  setMenuOpen(false);
                }}
              >
                <FaClipboardList /> Diyet Listem
              </button>
            </nav>
          </div>
        )}

        <div className="flex-grow-1" style={{ marginLeft: !isMobile ? 250 : 0 }}>
          <div
            className="d-flex justify-content-between align-items-center p-3 border-bottom"
            style={{ backgroundColor: primaryColor }}
          >
            {isMobile && (
              <button className="btn btn-outline-dark" onClick={() => setMenuOpen(!menuOpen)}>
                <GiHamburgerMenu />
              </button>
            )}
            <h1 className="h5 mb-0">
              {activePage === "home"
                ? "Ana Sayfa"
                : activePage === "health"
                ? "Sağlık Bilgilerim"
                : "Diyet Listem"}
            </h1>
            <button onClick={handleLogout} className="btn btn-sm btn-danger fw-bold">
              Çıkış Yap
            </button>
          </div>

          <div className="p-4">
            {activePage === "home" && (
              <>
                <DietitianSelection
                  dietitians={dietitians}
                  handleSelectDietitian={handleSelectDietitian}
                  myDietitian={myDietitian}
                  healthInfo={healthInfo}
                  dietTypeName={dietTypeName} // ✅ props olarak ekledik
                />
                <hr className="my-4" />
                <DietTypeSelect dietTypeName={dietTypeName} 
                onDietTypeChange={(name) => setDietTypeName(name)}/> {/* ✅ props olarak ekledik */}
              </>
            )}

            {activePage === "health" && (
              <>
                <h3>🩺 Sağlık Bilgilerim</h3>
                <form onSubmit={handleSubmit}>
                  {[
                    ["Kan Basıncı", "bloodPressure"],
                    ["Kan Şekeri", "bloodSugar"],
                    ["Kolesterol", "cholesterol"],
                    ["Alerjiler", "allergies"],
                    ["İlaçlar", "medications"],
                    ["Diğer Durumlar", "otherHealthConditions"],
                  ].map(([label, name]) => (
                    <div key={name} className="mb-3">
                      <label className="form-label">{label}</label>
                      <input
                        type="text"
                        className="form-control"
                        name={name}
                        value={healthInfo ? healthInfo[name] : ""}
                        onChange={(e) =>
                          setHealthInfo({ ...healthInfo, [e.target.name]: e.target.value })
                        }
                        required={name !== "otherHealthConditions"}
                      />
                    </div>
                  ))}
                  <button type="submit" className="btn btn-success">
                    Kaydet
                  </button>
                </form>
              </>
            )}

            {activePage === "diet" && (
              dietList ? (
                <div className="card shadow p-4">
                  <h4>{dietList.name}</h4>
                  <p className="mb-2">📄 Açıklama: {dietList.description}</p>
                  <p>🍳 Kahvaltı: {dietList.morningMenu}</p>
                  <p>🍛 Öğle: {dietList.lunchMenu}</p>
                  <p>🍽️ Akşam: {dietList.dinnerMenu}</p>
                  <p>⏱️ Süre: {dietList.duration} gün</p>
                  <p>🍽️ Diyet Tipi: {dietList.dietTypeName}</p>
                  <p className="text-muted mt-2">
                    🧑‍⚕️ Diyetisyen: {dietList.dietitianName}<br />
                    👤 Danışan: {dietList.clientName}<br />
                    📅 Oluşturulma: {new Date(dietList.createdAt).toLocaleDateString()}
                  </p>
                </div>
              ) : (
                <p className="text-muted">Henüz tanımlanmış bir diyet listeniz bulunmamaktadır.</p>
              )
            )}
          </div>
        </div>
      </div>
      <ToastContainer />
    </>
  );
}
