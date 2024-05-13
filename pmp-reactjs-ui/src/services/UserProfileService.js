
let userProfile = null;

export const setUserProfile = (userData) => {
    userProfile = {
        "userName": userData.userName,
        "firstName": userData.firstName,
        "lastName": userData.lastName,
        "email": userData.email,
        "orgName": userData.orgName,
        "partnerType": userData.partnerType
    }
} 


export const getUserProfile = () => {
    return userProfile; 
} 