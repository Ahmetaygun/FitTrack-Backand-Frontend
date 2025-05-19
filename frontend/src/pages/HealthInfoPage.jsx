import React, { useEffect, useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import { useNavigate } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";

export default function HealthInfoPage() {
  const [healthInfo, setHealthInfo] = useState({
    bloodPressure: "",
    bloodSugar: "",
    cholesterol: "",
    allergies: "",
    medications: "",
    otherHealthConditions: "",
  });

  const navigate = useNavigate();

  useEffect(() => {
    fetchHealthInfo();
  }, []);

  const fetchHealthInfo = async () => {
    try {
      const token = localStorage.getItem("jwtToken");
      const response = await axios.get("http://localhost:8080/api/clients/health-info", {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (response.data) {

        const data =
          typeof response.data === "string"
            ? JSON.parse(response.data)
            : response.data;

        // Boş gelen alanları da güvence altına alalım
        setHealthInfo({
          bloodPressure: data.bloodPressure || "",
          bloodSugar: data.bloodSugar || "",
          cholesterol: data.cholesterol || "",
          allergies: data.allergies || "",
          medications: data.medications || "",
          otherHealthConditions: data.otherHealthConditions || "",
        });
      }
    } catch (error) {
      toast.error("❌ Sağlık bilgileri yüklenemedi.");
    }
  };

  const handleChange = (e) => {
    setHealthInfo({ ...healthInfo, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("jwtToken");
      await axios.post("http://localhost:8080/api/clients/health-info", healthInfo, {
        headers: { Authorization: `Bearer ${token}` },
      });
      toast.success("✅ Sağlık bilgileri başarıyla kaydedildi!");
    } catch (error) {
      toast.error("❌ Kaydederken bir hata oluştu.");
    }
  };

  return (
    <div className="container mt-5 pb-5">
      <h2 className="mb-4">🩺 Sağlık Bilgilerim</h2>

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
              value={healthInfo[name]}
              onChange={handleChange}
              required={name !== "otherHealthConditions"}
            />
          </div>
        ))}

        <div className="d-flex justify-content-between align-items-center mt-4">
          <button type="submit" className="btn btn-success">
            Kaydet
          </button>
          <button
            type="button"
            className="btn btn-outline-primary"
            onClick={() => navigate("/client-dashboard")}
          >
            Anasayfaya Dön
          </button>
        </div>
      </form>

      <ToastContainer position="top-center" autoClose={2000} />
    </div>
  );
}
