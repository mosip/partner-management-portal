import { createSlice } from "@reduxjs/toolkit";


const notificationsSlice = createSlice({
    name: "headerNotifications",
    initialState: {
        headerNotifications: [],
        lastSeenDtimes: null,
        notificationSeenDtimes: null,
        dismissClicked: false,
    },
    reducers: {
        updateHeaderNotifications: (state, action) => {
            state.headerNotifications = action.payload;
        },
        updateLastSeenDtimes: (state, action) => {
            state.lastSeenDtimes = action.payload;
        },
        updateNotificationSeenDtimes: (state, action) => {
            state.notificationSeenDtimes = action.payload;
        },
        updateDismissClicked: (state, action) => {
            state.dismissClicked = action.payload;
        },
    },
});

export const {updateHeaderNotifications, updateLastSeenDtimes, updateNotificationSeenDtimes, updateDismissClicked } = notificationsSlice.actions;

export default notificationsSlice.reducer;