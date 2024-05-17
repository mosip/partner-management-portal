import { useEffect, useState } from "react";
import HttpService from "../services/HttpService";
import { getUserProfile } from "../services/UserProfileService";

function ErrorPage ({errorMessage, cancelErrorMsg, upload}) {
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const langCode = getUserProfile().langCode;
                let response;
                try {
                    response = await HttpService.get('/i18n/' + langCode + '.json');
                } catch(err) {
                    response = await HttpService.get('/i18n/eng.json');
                }
                const serverErrors = response.data.serverError;
                // console.log(serverErrors);
                if (serverErrors.errorMessage) {
                    setError(serverErrors.errorMessage);
                } else {
                    setError(errorMessage)
                }

            } catch (err) {
                console.error(err);
            }
        };

        fetchData();
    }, []);
    
    return(
        <>
        {!upload && (
            <div className="flex justify-end max-w-7xl">
                <div className="flex justify-between items-center max-w-96 min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 mr-10">
                    <div>
                        <p className="text-xs font-semibold text-white break-words">
                            {error}
                        </p>
                    </div>
                    <div className="mr-3 ml-5">
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="16.828"
                            height="16.828"
                            viewBox="0 0 16.828 16.828"
                            onClick={cancelErrorMsg}
                        >
                            <path
                                id="close_FILL0_wght400_GRAD0_opsz48"
                                d="M 23.27308082580566 25.05710983276367 L 22.91953086853027 24.70355033874512 L 17.35000038146973 19.13401985168457 L 11.7804708480835 24.70355033874512 L 11.42691993713379 25.05710983276367 L 11.0733699798584 24.70355033874512 L 9.996450424194336 23.62663078308105 L 9.642889976501465 23.27308082580566 L 9.996450424194336 22.91953086853027 L 15.56597995758057 17.35000038146973 L 9.996450424194336 11.7804708480835 L 9.642889976501465 11.42691993713379 L 9.996450424194336 11.07336044311523 L 11.07338047027588 9.996439933776855 L 11.42693042755127 9.642889976501465 L 11.78048038482666 9.996450424194336 L 17.35000038146973 15.5659704208374 L 22.91953086853027 9.996450424194336 L 23.27308082580566 9.642889976501465 L 23.62663078308105 9.996450424194336 L 24.70355033874512 11.0733699798584 L 25.05710983276367 11.42691993713379 L 24.70355033874512 11.7804708480835 L 19.13401985168457 17.35000038146973 L 24.70355033874512 22.91953086853027 L 25.05710983276367 23.27308082580566 L 24.70355033874512 23.62663078308105 L 23.62663078308105 24.70355033874512 L 23.27308082580566 25.05710983276367 Z"
                                transform="translate(-8.936 -8.936)"
                                fill="#fff"
                            />
                        </svg>
                    </div>
                </div>
            </div>
        )}
        {upload && (
            <div className="fixed inset-0 flex mt-[122px] justify-center">
                <div className="bg-moderate-red md:w-[400px] w-[60%] h-[50px] flex items-center justify-between p-4">
                    <p className="text-sm font-semibold text-white break-words">
                        {errorMessage}
                    </p>
                    <svg
                        className="cursor-pointer"
                        xmlns="http://www.w3.org/2000/svg"
                        width="16.828"
                        height="16.828"
                        viewBox="0 0 16.828 16.828"
                        onClick={cancelErrorMsg}
                    >
                        <path
                            id="close_FILL0_wght400_GRAD0_opsz48"
                            d="M 23.27308082580566 25.05710983276367 L 22.91953086853027 24.70355033874512 L 17.35000038146973 19.13401985168457 L 11.7804708480835 24.70355033874512 L 11.42691993713379 25.05710983276367 L 11.0733699798584 24.70355033874512 L 9.996450424194336 23.62663078308105 L 9.642889976501465 23.27308082580566 L 9.996450424194336 22.91953086853027 L 15.56597995758057 17.35000038146973 L 9.996450424194336 11.7804708480835 L 9.642889976501465 11.42691993713379 L 9.996450424194336 11.07336044311523 L 11.07338047027588 9.996439933776855 L 11.42693042755127 9.642889976501465 L 11.78048038482666 9.996450424194336 L 17.35000038146973 15.5659704208374 L 22.91953086853027 9.996450424194336 L 23.27308082580566 9.642889976501465 L 23.62663078308105 9.996450424194336 L 24.70355033874512 11.0733699798584 L 25.05710983276367 11.42691993713379 L 24.70355033874512 11.7804708480835 L 19.13401985168457 17.35000038146973 L 24.70355033874512 22.91953086853027 L 25.05710983276367 23.27308082580566 L 24.70355033874512 23.62663078308105 L 23.62663078308105 24.70355033874512 L 23.27308082580566 25.05710983276367 Z"
                            transform="translate(-8.936 -8.936)"
                            fill="#fff"
                        />
                    </svg>
                </div>
            </div>
        )}
        </>
    );
}

export default ErrorPage;