import './App.css';
import AppRoutes from './AppRoutes';
import { loadAppConfig } from './services/ConfigService.js';
import { setupResponseInterceptor } from './services/HttpService.js';

await loadAppConfig();
setupResponseInterceptor();

function App() {

  return (
    <AppRoutes></AppRoutes>
  );
}

export default App;
