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