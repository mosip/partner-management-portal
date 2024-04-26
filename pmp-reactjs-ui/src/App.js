import './App.css';
import AppRoutes from './auth/AppRoutes';
import { loadAppConfig } from './services/ConfigService.js';

await loadAppConfig();

function App() {  
  return (
    <AppRoutes></AppRoutes>
  );
}

export default App;
