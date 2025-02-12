import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Login.css'; // Import the CSS file for styling

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('CUSTOMER'); // Default role
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError('');
        try {
            const response = await axios.post('http://localhost:8080/api/users/login', null, {
                params: { email, password }
            });
            if (response.status === 200) {
                alert(response.data);
                // Clear the fields after successful login
                setEmail(''); // Clear email field
                setPassword(''); // Clear password field
                setRole('CUSTOMER'); // Reset to default role
                navigate('/categories'); // Redirect to the category page
            }
        } catch (err) {
            console.error(err);
            if (err.response && err.response.data) {
                setError(err.response.data || 'An error occurred during login.');
            } else {
                setError('An unexpected error occurred.');
            }
        }
    };

    const handleSignUp = () => {
        navigate('/register'); // Redirect to the registration page
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            <form onSubmit={handleLogin} className="login-form">
                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input
                        type="email"
                        id="email"
                        placeholder="Enter your email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        autoComplete="off" // Prevent autofill
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        placeholder="Enter your password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        autoComplete="off" // Prevent autofill
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="role">Role</label>
                    <select id="role" value={role} onChange={(e) => setRole(e.target.value)}>
                        <option value="CUSTOMER">Login as Customer</option>
                        <option value="ADMIN">Login as Admin</option>
                    </select>
                </div>
                <button type="submit" className="login-button">Login</button>
                {error && <p className="error-message">{error}</p>}
            </form>
            <p className="signup-link">
                Don't have an account? <span onClick={handleSignUp} className="link">Sign Up</span>
            </p>
        </div>
    );
};

export default Login;