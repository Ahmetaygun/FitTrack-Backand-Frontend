import React from 'react'

const Services = () => {
  const services = [
    {
      title: "Kişisel Beslenme Planı",
      description: "Size özel hazırlanmış, yaşam tarzınıza uygun beslenme programları",
      icon: "🎯"
    },
    {
      title: "Online Danışmanlık",
      description: "Uzaktan takip ve sürekli destek ile hedeflerinize ulaşın",
      icon: "💻"
    },
    {
      title: "Kilo Yönetimi",
      description: "Bilimsel ve sürdürülebilir kilo verme/alma programları",
      icon: "⚖️"
    },
    {
      title: "Sporcu Beslenmesi",
      description: "Performansınızı artıracak beslenme stratejileri",
      icon: "💪"
    }
  ]

  return (
    <section id="services" className="py-5">
      <div className="container">
        <div className="text-center mb-5">
          <h2 className="display-5 fw-bold mb-3" style={{ color: 'var(--secondary)' }}>Hizmetlerimiz</h2>
          <p className="lead text-muted">Size özel beslenme çözümleri sunuyoruz</p>
        </div>
        <div className="row g-4">
          {services.map((service, index) => (
            <div key={index} className="col-md-6 col-lg-3">
              <div className="card h-100 border-0 shadow-sm hover-shadow transition">
                <div className="card-body text-center p-4">
                  <div className="display-5 mb-3">{service.icon}</div>
                  <h3 className="h5 mb-3">{service.title}</h3>
                  <p className="text-muted mb-0">{service.description}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}

export default Services