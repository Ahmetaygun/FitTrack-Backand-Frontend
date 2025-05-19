import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Logo from '../assets/logo.png';

const Register = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    age: '',
    gender: ''
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post('http://localhost:8080/api/auth/register', formData);
      alert('Kayıt başarılı! Şimdi giriş yapabilirsiniz.');
      navigate('/login');
    } catch (error) {
      console.error('Kayıt başarısız:', error.response?.data?.message || error.message);
      alert('Kayıt başarısız. Bilgilerinizi kontrol edin.');
    }
  };

  return (
    <div className="container max-vh-100 d-flex align-items-center justify-content-center py-2"
         style={{ background: 'linear-gradient(135deg, rgba(144, 238, 144, 0.1) 0%, rgba(255, 255, 255, 0.1) 100%)' }}>
      <div className="card border-0 shadow-lg" style={{ maxWidth: "500px", width: "100%", borderRadius: "20px" }}>
        <div className="card-body p-5">
          <div className="text-center mb-4">
            <img src={Logo} alt="Logo" style={{ height: '80px' }} className="mb-3" />
            <h2 className="mb-3 fw-bold" style={{ color: 'var(--secondary)' }}>Hesap Oluştur</h2>
            <p className="text-muted">Sağlıklı yaşam için ilk adımı at</p>
          </div>
          <form onSubmit={handleSubmit}>
            <div className="row g-3">
              {/* Ad */}
              <div className="col-12">
                <div className="input-group">
                  <span className="input-group-text bg-light border-end-0">
                    <i className="bi bi-person" style={{ color: 'var(--primary)' }}></i>
                  </span>
                  <input
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    className="form-control border-start-0 ps-0"
                    placeholder="Ad"
                    style={{ backgroundColor: '#f8f9fa' }}
                    required
                  />
                </div>
              </div>
              {/* Soyad */}
              <div className="col-12">
                <div className="input-group">
                  <span className="input-group-text bg-light border-end-0">
                    <i className="bi bi-person" style={{ color: 'var(--primary)' }}></i>
                  </span>
                  <input
                    type="text"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                    className="form-control border-start-0 ps-0"
                    placeholder="Soyad"
                    style={{ backgroundColor: '#f8f9fa' }}
                    required
                  />
                </div>
              </div>
              {/* E-posta */}
              <div className="col-12">
                <div className="input-group">
                  <span className="input-group-text bg-light border-end-0">
                    <i className="bi bi-envelope" style={{ color: 'var(--primary)' }}></i>
                  </span>
                  <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    className="form-control border-start-0 ps-0"
                    placeholder="E-posta"
                    style={{ backgroundColor: '#f8f9fa' }}
                    required
                  />
                </div>
              </div>
              {/* Şifre */}
              <div className="col-12">
                <div className="input-group">
                  <span className="input-group-text bg-light border-end-0">
                    <i className="bi bi-lock" style={{ color: 'var(--primary)' }}></i>
                  </span>
                  <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    className="form-control border-start-0 ps-0"
                    placeholder="Şifre"
                    style={{ backgroundColor: '#f8f9fa' }}
                    required
                  />
                </div>
              </div>
              {/* Yaş */}
              <div className="col-12">
                <div className="input-group">
                  <span className="input-group-text bg-light border-end-0">
                    <i className="bi bi-calendar" style={{ color: 'var(--primary)' }}></i>
                  </span>
                  <input
                    type="number"
                    name="age"
                    value={formData.age}
                    onChange={handleChange}
                    className="form-control border-start-0 ps-0"
                    placeholder="Yaş"
                    min="1"
                    style={{ backgroundColor: '#f8f9fa' }}
                    required
                  />
                </div>
              </div>
              {/* Cinsiyet */}
              <div className="col-12">
                <select
                  className="form-select"
                  name="gender"
                  value={formData.gender}
                  onChange={handleChange}
                  required
                >
                  <option value="">Cinsiyet Seçiniz</option>
                  <option value="Male">Erkek</option>
                  <option value="Female">Kadın</option>
                  <option value="Other">Diğer</option>
                </select>
              </div>

              {/* Kayıt Butonu */}
              <div className="col-12">
                <button
                  type="submit"
                  className="btn btn-success w-100 rounded-pill py-3 fw-bold"
                  style={{
                    backgroundColor: 'var(--primary)',
                    borderColor: 'var(--primary)',
                    boxShadow: '0 4px 15px rgba(144, 238, 144, 0.3)'
                  }}
                >
                  Kaydol
                </button>
              </div>
            </div>
            <div className="text-center mt-4">
              <span className="text-muted">Zaten hesabın var mı? </span>
              <Link
                to="/login"
                className="text-decoration-none fw-bold"
                style={{ color: 'var(--primary)' }}
              >
                Giriş Yap
              </Link>
            </div>
          </form>
          <button
            onClick={() => navigate('/')}
            className="btn btn-link text-decoration-none position-absolute top-0 start-0 m-3"
            style={{ color: 'var(--secondary)' }}
          >
            ← Geri
          </button>
        </div>
      </div>
    </div>
  );
};

export default Register;
