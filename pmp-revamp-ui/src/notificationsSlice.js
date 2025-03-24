import { createSlice } from "@reduxjs/toolkit";


const notificationsSlice = createSlice({
    name: "notifications",
    initialState: {
        notifications: []
    },
    reducers: {
        fetchNotificationFromHeader: (state, action) => {
            state.notifications = action.payload;
        },
    },
});

export const {fetchNotificationFromHeader} = notificationsSlice.actions;

export default notificationsSlice.reducer;