import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import './i18n';
import { loadAppConfig } from './services/ConfigService.js';

const root = ReactDOM.createRoot(document.getElementById('root'));

loadAppConfig().then(() => {
    if (!sessionStorage.getItem('appConfig')) {
        root.render(
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', fontFamily: 'Inter, sans-serif', color: '#333' }}>
                <p>Failed to load application configuration. Please refresh the page.</p>
            </div>
        );
        return;
    }
    root.render(
        <App />
    );
}).catch(() => {
    root.render(
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', fontFamily: 'Inter, sans-serif', color: '#333' }}>
            <p>Failed to load application configuration. Please refresh the page.</p>
        </div>
    );
});

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
