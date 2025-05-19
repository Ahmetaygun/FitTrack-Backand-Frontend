import React from 'react'

const Footer = () => {
  return (
    <footer className="bg-dark text-light py-5">
      <div className="container">
        <div className="row g-4 justify-content-center">
          <div className="col-lg-3 col-md-6 text-center">
            <h4 className="h6 mb-3">Hızlı Bağlantılar</h4>
            <ul className="link">
              <li><a href="#home" className="text-decoration-none text-light">Ana Sayfa</a></li>
              <li><a href="#services" className="text-decoration-none text-light">Hizmetler</a></li>
              <li><a href="#about" className="text-decoration-none text-light">Hakkımızda</a></li>
              <li><a href="#contact" className="text-decoration-none text-light">İletişim</a></li>
            </ul>
          </div>
          <div className="col-lg-3 col-md-6 text-center">
            <h4 className="h6 mb-3">İletişim</h4>
            <ul className="list-unstyled">
              <li>📍  Malatya, Türkiye</li>
              <li>📞 +90 555 444 44 44</li>
              <li>✉️  info@fittrack.com</li>
            </ul>
          </div>
          <div className="col-lg-3 col-md-6 text-center">
            <h4 className="h6 mb-3">Sosyal Medya</h4>
            <div className="d-flex justify-content-center gap-3">
              <a href="#" className="text-decoration-none text-light fs-5">📱</a>
              <a href="#" className="text-decoration-none text-light fs-5">🐦</a>
              <a href="#" className="text-decoration-none text-light fs-5">👥</a>
            </div>
          </div>
        </div>
        <hr className="my-4" />
        <div className="text-center">
          <small>&copy; 2025 FitTrack. Tüm hakları saklıdır.</small>
        </div>
      </div>
    </footer>
  )
}

export default Footer