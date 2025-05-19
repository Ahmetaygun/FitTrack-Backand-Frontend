import React, { useEffect, useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";

export default function DietTypeSelect({ dietTypeName, onDietTypeChange }) {
  const [dietTypes, setDietTypes] = useState([]);
  const [selectedId, setSelectedId] = useState("");

  useEffect(() => {
    fetchDietTypes();
  }, []);

  const fetchDietTypes = async () => {
    try {
      const token = localStorage.getItem("jwtToken");
      const response = await axios.get("http://localhost:8080/api/diet-types/all", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setDietTypes(response.data);
    } catch (error) {
      toast.error("Diyet tipleri alınamadı.");
    }
  };

  const handleSelection = async () => {
    if (!selectedId) return;
    try {
      const token = localStorage.getItem("jwtToken");
      await axios.post(
        "http://localhost:8080/api/clients/select-diet",
        { dietTypeId: selectedId },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const selected = dietTypes.find((type) => type.id === Number(selectedId));
      if (selected && onDietTypeChange) {
        onDietTypeChange(selected.name); // 👈 güncel isim parent’a gönderilir
      }

      toast.success("✅ Diyet tipi başarıyla seçildi!");
    } catch (error) {
      toast.error("❌ Diyet tipi seçilemedi.");
    }
  };

  return (
    <div className="mb-5">
      <h4 className="mb-3">🥗 Diyet Tipi Seç</h4>
      <p><strong>Seçili Diyet Tipi: </strong> {dietTypeName || "Henüz seçilmedi"}</p>
      <select
        className="form-select mb-3"
        value={selectedId}
        onChange={(e) => setSelectedId(e.target.value)}
      >
        <option value="">Bir diyet tipi seçiniz</option>
        {dietTypes.map((type) => (
          <option key={type.id} value={type.id}>
            {type.name} - {type.description}
          </option>
        ))}
      </select>
      <button onClick={handleSelection} className="btn btn-success">
        Seçimi Kaydet
      </button>
    </div>
  );
}
