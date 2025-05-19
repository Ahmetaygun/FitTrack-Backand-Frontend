import { useState } from 'react'
import { Link } from 'react-router-dom'
import Logo from '../assets/logo.png'
import React from 'react'

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false)

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-white fixed-top shadow">
      <div className="container">
        <Link className="navbar-brand d-flex align-items-center" to="/">
          <img src={Logo} alt="Fittrack Logo" className="me-2" style={{ height: '60px', width:'80px' }} />
          <span className="fw-semibold" style={{ color: 'var(--secondary)' }}>FitTrack</span>
        </Link>

        <button 
          className="navbar-toggler" 
          type="button" 
          onClick={() => setIsOpen(!isOpen)}
          aria-controls="navbarNav" 
          aria-expanded="false" 
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className={`collapse navbar-collapse ${isOpen ? 'show' : ''}`} id="navbarNav">
          <ul className="navbar-nav ms-auto align-items-center">
            <li className="nav-item">
              <a className="nav-link" href="#home">Ana Sayfa</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#services">Hizmetler</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#about">Hakkımızda</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#contact">İletişim</a>
            </li>
            <li className="nav-item ms-lg-3 mt-2 mt-lg-0">
              <Link 
                to="/register"
                className="btn btn-outline-success me-2 rounded-pill px-4" 
                style={{ 
                  color: 'var(--primary)',
                  borderColor: 'var(--primary)'
                }}
              >
                Kayıt Ol
              </Link>
              <Link 
                to="/login"
                className="btn btn-success rounded-pill px-4" 
                style={{ 
                  backgroundColor: 'var(--primary)',
                  borderColor: 'var(--primary)'
                }}
              >
                Giriş Yap
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  )
}

export default Navbar