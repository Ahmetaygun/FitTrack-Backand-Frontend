import React from 'react'
import Register from "../pages/Register"
import { useNavigate } from 'react-router-dom'

const Hero = () => {
  const navigate = useNavigate();

  return (
    <section id="home" className="bg-light py-5">
      <div className="container">
        <div className="row align-items-center min-vh-75 py-5">
          <div className="col-lg-6">
            <h1 className="display-4 fw-bold mb-4" style={{ color: 'var(--secondary)' }}>
              Diyetisyeniniz Cebinizde!
            </h1>
            <p className="lead text-muted mb-4">
              Sağlıklı yaşam yolculuğunuzda size rehberlik ediyoruz. Kişiselleştirilmiş beslenme planları ve uzman desteğiyle hedeflerinize ulaşın.
            </p>
            <button onClick={() => navigate("/register")} className="btn btn-success btn-lg px-5 rounded-pill"
              style={{ backgroundColor: 'var(--primary)', borderColor: 'var(--primary)' }}>
              Hemen Başlayın
            </button>
          </div>
          <div className="col-lg-6 mt-5 mt-lg-0">
            <img src="/src/assets/anasayfa.webp" alt="Healthy Lifestyle" className="img-fluid rounded-4 shadow" />
          </div>
        </div>
      </div>
    </section>
  )
}

export default Hero