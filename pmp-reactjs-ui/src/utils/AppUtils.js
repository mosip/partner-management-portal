import { useEffect } from 'react';

export const formatDate = (dateString, format) => {
    if (!dateString) return '-';
    const date = new Date(dateString);
    if (format === 'dateTime') {
        return date.toLocaleString();
    } else if (format === 'date') {
        return date.toLocaleDateString();
    } else {
        return '-';
    }
};

export const getPartnerTypeDescription = (partnerType) => {
    if (partnerType) {
        if (partnerType === "Device_Provider") {
            return "Device Provider"
        }
        else if (partnerType === "FTM_Provider") {
            return "FTM Provider"
        }
        else if (partnerType === "Auth_Partner") {
            return "Authentication Partner"
        }
    }
}

export const useOutsideClick = (ref, callback) => {
    useEffect(() => {
        function handleClickOutside(event) {
            if (ref.current && !ref.current.contains(event.target)) {
                callback();
            }
        }
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, [ref, callback]);
};