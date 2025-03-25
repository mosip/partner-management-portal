import { createSlice } from "@reduxjs/toolkit";


const notificationsSlice = createSlice({
    name: "headerNotifications",
    initialState: {
        headerNotifications: []
    },
    reducers: {
        updateHeaderNotifications: (state, action) => {
            state.headerNotifications = action.payload;
        },
    },
});

export const {updateHeaderNotifications} = notificationsSlice.actions;

export default notificationsSlice.reducer;