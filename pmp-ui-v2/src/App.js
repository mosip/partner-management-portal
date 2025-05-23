import './App.css';
import AppRoutes from './AppRoutes';
import { loadAppConfig } from './services/ConfigService.js';
import store from './store';
import { Provider } from 'react-redux';

await loadAppConfig();

function App() {

  return (
    <Provider store={store}>
      <AppRoutes></AppRoutes>
    </Provider>
  );
}

export default App;
