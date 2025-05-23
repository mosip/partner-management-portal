import { configureStore } from '@reduxjs/toolkit';
import notificationsReducer from "./notificationsSlice";

const store = configureStore({
    reducer: {
        headerNotifications: notificationsReducer, // Notifications reducer
    }
});

export default store;