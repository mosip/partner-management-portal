import React from 'react';

function LoadingCount () {
    return (
        <div className='flex justify-center items-center m-2'>
            <div className='h-1 w-1 mx-px bg-[#6D1C00] rounded-full animate-bounce [animation-delay:-0.3s]'></div>
            <div className='h-1 w-1 mx-px bg-[#6D1C00] rounded-full animate-bounce [animation-delay:-0.15s]'></div>
            <div className='h-1 w-1 mx-px bg-[#6D1C00] rounded-full animate-bounce'></div>
        </div>
    )
}

export default LoadingCount;