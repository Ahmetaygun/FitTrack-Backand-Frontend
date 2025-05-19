import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Logo from '../assets/logo.png';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { jwtDecode } from "jwt-decode"; 

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', {
        email,
        password
      });

      const token = response.data.token;
      console.log('✅ Giriş Başarılı, Token:', token);

      localStorage.setItem('jwtToken', token);

      const decodedToken = jwtDecode(token);
      const role = decodedToken.role?.replace('ROLE_', '').toUpperCase();
      console.log('Token’den Gelen Rol:', role);

      toast.success("Başarıyla giriş yapıldı!", {
        position: "top-center",
        autoClose: 1000,
      });

      setTimeout(() => {
        if (role === 'ADMIN') {
          navigate('/admin-dashboard');
        } else if (role === 'DIETITIAN') {
          navigate('/dietitian-dashboard');
        } else if (role === 'CLIENT') {
          navigate('/client-dashboard');
        } else {
          toast.error("Tanımsız rol! Ana sayfaya yönlendiriliyorsunuz.");
          navigate('/');
        }
      }, 1000);

    } catch (error) {
      console.error('❌ Giriş Başarısız:', error.response?.data?.message || error.message);
      toast.error('Giriş başarısız: Email veya şifre yanlış.', {
        position: "top-center",
        autoClose: 3000,
      });
    }
  };

  return (
    <>
      <div className="container max-vh-100 d-flex align-items-center justify-content-center"
        style={{ background: 'linear-gradient(135deg, rgba(144, 238, 144, 0.1) 0%, rgba(255, 255, 255, 0.1) 100%)' }}>
        <div className="card border-0 shadow-lg" style={{ maxWidth: "400px", width: "100%", borderRadius: "20px" }}>
          <div className="card-body p-5">
            <div className="text-center mb-4">
              <img src={Logo} alt="Logo" style={{ height: '80px' }} className="mb-3" />
              <h2 className="mb-3 fw-bold" style={{ color: 'var(--secondary)' }}>Hoş Geldiniz</h2>
              <p className="text-muted">Hesabınıza giriş yapın</p>
            </div>

            <form onSubmit={handleSubmit}>
              <div className="mb-4">
                <div className="input-group">
                  <span className="input-group-text bg-light border-end-0">
                    <i className="bi bi-envelope" style={{ color: 'var(--primary)' }}></i>
                  </span>
                  <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="form-control border-start-0 ps-0"
                    placeholder="E-posta"
                    style={{ backgroundColor: '#f8f9fa' }}
                    required
                  />
                </div>
              </div>

              <div className="mb-4">
                <div className="input-group">
                  <span className="input-group-text bg-light border-end-0">
                    <i className="bi bi-lock" style={{ color: 'var(--primary)' }}></i>
                  </span>
                  <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="form-control border-start-0 ps-0"
                    placeholder="Şifre"
                    style={{ backgroundColor: '#f8f9fa' }}
                    required
                  />
                </div>
              </div>

              <div className="mb-4 d-flex justify-content-between align-items-center">
                <div className="form-check">
                  <input type="checkbox" className="form-check-input" id="remember" />
                  <label className="form-check-label text-muted" htmlFor="remember">Beni hatırla</label>
                </div>
                <a href="#" className="text-decoration-none" style={{ color: 'var(--primary)' }}>Şifremi unuttum</a>
              </div>

              <button
                type="submit"
                className="btn btn-success w-100 rounded-pill py-3 mb-4 fw-bold"
                style={{
                  backgroundColor: 'var(--primary)',
                  borderColor: 'var(--primary)',
                  boxShadow: '0 4px 15px rgba(144, 238, 144, 0.3)'
                }}
              >
                Giriş Yap
              </button>

              <div className="text-center">
                <span className="text-muted">Hesabın yok mu? </span>
                <Link to="/register" className="text-decoration-none fw-bold" style={{ color: 'var(--primary)' }}>
                  Kaydol
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
      <ToastContainer />
    </>
  );
};

export default Login;
