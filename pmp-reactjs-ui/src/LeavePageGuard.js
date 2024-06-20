import { useEffect } from 'react';

function LeavePageGuard({ children }) {

  useEffect(() => {
    const handleBeforeUnload = (event) => {
      event.preventDefault();
    };

    window.addEventListener('beforeunload', handleBeforeUnload);

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, []);

  return children;
}

export default LeavePageGuard;
