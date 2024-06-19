import cancelIcon from '../../svg/cancel_icon.svg';

function SuccessMessage({ successMsg, clickOnCancel}) {
    return (
        <>
            <div className="mr-6">
                <p className="text-sm text-white break-words font-inter">
                    {successMsg}
                </p>
            </div>
            <div className="mr-3 ml-5 absolute right-2">
                <img src={cancelIcon} alt="" onClick={clickOnCancel}></img>
            </div>
        </>
    );
}

export default SuccessMessage;