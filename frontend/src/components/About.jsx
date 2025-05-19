import React from 'react'

const About = () => {
  return (
    <section id="about" className="py-5 bg-light">
      <div className="container">
        <div className="row align-items-center">
          <div className="col-lg-6">
            <img src="/src/assets/about-us.jpeg" alt="About Us" className="img-fluid rounded-4 shadow" />
          </div>
          <div className="col-lg-6 mt-5 mt-lg-0">
            <h2 className="display-5 fw-bold mb-4" style={{ color: 'var(--secondary)' }}>Hakkımızda</h2>
            <p className="lead mb-4">
              FitTrack olarak, sağlıklı yaşam yolculuğunuzda yanınızdayız. Uzman kadromuz ve yenilikçi yaklaşımımızla, beslenme alışkanlıklarınızı iyileştirmenize yardımcı oluyoruz.
            </p>
            <p className="text-muted mb-5">
              Modern teknoloji ve bilimsel yöntemlerle, size özel beslenme programları hazırlıyor, hedeflerinize ulaşmanız için destek oluyoruz.
            </p>
            <div className="row g-4">
              <div className="col-6">
                <div className="card border-0 bg-white shadow-sm">
                  <div className="card-body text-center p-4">
                    <h3 className="display-6 fw-bold mb-2" style={{ color: 'var(--primary)' }}>1000+</h3>
                    <p className="text-muted mb-0">Mutlu Danışan</p>
                  </div>
                </div>
              </div>
              <div className="col-6">
                <div className="card border-0 bg-white shadow-sm">
                  <div className="card-body text-center p-4">
                    <h3 className="display-6 fw-bold mb-2" style={{ color: 'var(--primary)' }}>5+</h3>
                    <p className="text-muted mb-0">Yıllık Deneyim</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

export default About