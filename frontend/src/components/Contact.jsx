import React from 'react'

const Contact = () => {
  return (
    <section id="contact" className="py-5">
      <div className="container">
        <div className="text-center mb-5">
          <h2 className="display-5 fw-bold mb-3" style={{ color: 'var(--secondary)' }}>İletişime Geçin</h2>
          <p className="lead text-muted">Sağlıklı yaşam yolculuğunuza bugün başlayın</p>
        </div>
        <div className="row justify-content-center">
          <div className="col-lg-8">
            <div className="card border-0 shadow-sm">
              <div className="card-body p-4 p-md-5">
                <form>
                  <div className="row g-4">
                    <div className="col-md-6">
                      <input type="text" className="form-control" placeholder="Adınız" />
                    </div>
                    <div className="col-md-6">
                      <input type="email" className="form-control" placeholder="E-posta" />
                    </div>
                    <div className="col-12">
                      <input type="text" className="form-control" placeholder="Konu" />
                    </div>
                    <div className="col-12">
                      <textarea className="form-control" rows="4" placeholder="Mesajınız"></textarea>
                    </div>
                    <div className="col-12">
                      <button 
                        type="submit" 
                        className="btn btn-success w-100 py-3 rounded-pill"
                        style={{ 
                          backgroundColor: 'var(--primary)',
                          borderColor: 'var(--primary)'
                        }}
                      >
                        Gönder
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

export default Contact