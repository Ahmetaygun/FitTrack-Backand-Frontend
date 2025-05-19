import React from "react";
import { FaUserMd, FaVial, FaClipboardList } from "react-icons/fa";

const DietitianSelection = ({ dietitians, handleSelectDietitian, myDietitian, healthInfo, dietTypeName }) => (
  <div>
    <h5 className="mb-3">👩‍⚕️ Diyetisyen Seçimi</h5>
    <div className="row g-3">
      {dietitians.map((dietitian) => (
        <div className="col-md-4" key={dietitian.id}>
          <div className="card border-success shadow-sm p-3">
            <h6 className="mb-2">{dietitian.firstName} {dietitian.lastName}</h6>
            <button
              className="btn btn-sm btn-success"
              onClick={() => handleSelectDietitian(dietitian.id)}
            >
              Seç
            </button>
          </div>
        </div>
      ))}
    </div>

    <hr className="my-4" />
    <h5 className="mb-3">🔍 Kısa Özet</h5>
    <div className="row g-3">
      {/* Diyetisyen Kartı */}
      <div className="col-md-4">
        <div className="card border-success">
          <div className="card-body d-flex align-items-center">
            <FaUserMd className="me-3 text-success fs-3" />
            <div>
              <h6 className="mb-0">Diyetisyen</h6>
              <strong>{myDietitian || "Henüz seçilmedi"}</strong>
            </div>
          </div>
        </div>
      </div>

      {/* Son Kan Verisi Kartı */}
      <div className="col-md-4">
        <div className="card border-primary">
          <div className="card-body d-flex align-items-center">
            <FaVial className="me-3 text-primary fs-3" />
            <div>
              <h6 className="mb-0">Son Kan Verisi</h6>
              {healthInfo ? (
  <div className="d-flex flex-column">
    <strong>Şeker: {healthInfo.bloodSugar}</strong>
    <strong>Kolesterol: {healthInfo.cholesterol}</strong>
    <strong>Tansiyon: {healthInfo.bloodPressure}</strong>
  </div>
) : (
  <strong>Henüz veri yok</strong>
)}

            </div>
          </div>
        </div>
      </div>

      {/* Seçili Diyet Tipi Kartı */}
      <div className="col-md-4">
        <div className="card border-info">
          <div className="card-body d-flex align-items-center">
            <FaClipboardList className="me-3 text-info fs-3" />
            <div>
              <h6 className="mb-0">Seçili Diyet Tipi</h6>
              <strong>{dietTypeName || "Henüz seçilmedi"}</strong>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
);

export default DietitianSelection;
