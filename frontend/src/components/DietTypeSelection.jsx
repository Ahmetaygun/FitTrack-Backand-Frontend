import React, { useState } from "react";
import { toast } from "react-toastify";
import axios from "axios";

const DietTypeSelection = ({ healthInfo }) => {
  const [selectedDietType, setSelectedDietType] = useState("");
  const handleDietTypeSubmit = async () => {
    if (!healthInfo) {
      toast.warning("Önce sağlık bilgilerinizi kaydedin!");
      return;
    }

    try {
      const token = localStorage.getItem("jwtToken");
      await axios.post(
        "http://localhost:8080/api/clients/select-diet-type",
        { dietType: selectedDietType },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      toast.success("Diyet tipi başarıyla kaydedildi!");
    } catch (error) {
      console.error("Diyet tipi kaydedilemedi:", error);
      toast.error("Diyet tipi kaydedilemedi!");
    }
  };

  return (
    <div>
      <h5 className="mb-3 text-warning">📋 Diyet Tipi Seçimi</h5>
      <select
        className="form-select mb-3"
        value={selectedDietType}
        onChange={(e) => setSelectedDietType(e.target.value)}
        disabled={!healthInfo}
      >
        <option value="">Diyet Tipi Seçin</option>
        {dietTypes.map((diet) => (
          <option key={diet} value={diet}>{diet}</option>
        ))}
      </select>
      <button
        className="btn btn-warning text-white"
        onClick={handleDietTypeSubmit}
        disabled={!healthInfo}
      >
        Diyet Tipini Kaydet
      </button>
    </div>
  );
};

export default DietTypeSelection;
