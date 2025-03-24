import { configureStore } from '@reduxjs/toolkit';
import notificationsReducer from "./notificationsSlice";

const store = configureStore({
    reducer: {
        notifications: notificationsReducer, // Notifications reducer
    }
});

export default store;