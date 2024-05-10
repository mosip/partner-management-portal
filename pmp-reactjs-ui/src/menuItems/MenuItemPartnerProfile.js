import { useState } from "react";

export const MenuItemPartnerProfile = (props) => {

    const [activeIcon, setActiveIcon] = useState(false);

    const PartnerProfile = () => {
        setActiveIcon(!activeIcon);
    };

    return (
        <div className="flex gap-x-5 items-center mt-4 pl-1" onClick={PartnerProfile}>
            <div className={`h-6 w-1 ${activeIcon ? 'bg-tory-blue' : null} rounded-e-lg`}></div>
            <div className="h-10 p-3 rounded-md shadow-md">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="19.897" height="19.897" viewBox="0 0 19.897 19.897">
                    <path id="library_books_FILL0_wght200_GRAD0_opsz24"
                        d="M147.417-833.75h6.359V-836.8h-6.359Zm0,6.128h7.943v-1.141h-7.943Zm0-2.945h7.943v-1.141h-7.943Zm-2.15,6.542a1.785,1.785,0,0,1-1.315-.528,1.785,1.785,0,0,1-.528-1.315v-12.288a1.785,1.785,0,0,1,.528-1.315,1.785,1.785,0,0,1,1.315-.528h12.288a1.785,1.785,0,0,1,1.315.528,1.785,1.785,0,0,1,.528,1.315v12.288a1.785,1.785,0,0,1-.528,1.315,1.785,1.785,0,0,1-1.315.528Zm0-1.141h12.288a.671.671,0,0,0,.483-.219.671.671,0,0,0,.219-.483v-12.288a.671.671,0,0,0-.219-.483.671.671,0,0,0-.483-.219H145.266a.671.671,0,0,0-.483.219.671.671,0,0,0-.219.483v12.288a.671.671,0,0,0,.219.483A.671.671,0,0,0,145.266-825.167Zm-3.423,4.564a1.785,1.785,0,0,1-1.315-.528,1.785,1.785,0,0,1-.528-1.315v-13.429h1.141v13.429a.671.671,0,0,0,.219.483.671.671,0,0,0,.483.219h13.429v1.141Zm2.721-18.256v0Z"
                        transform="translate(-139.75 840.25)"
                        fill={activeIcon ? "#1447b2" : "#7a7e82"} stroke={activeIcon ? "#1447b2" : "#7a7e82"} strokeWidth="0.5" />
                </svg>

            </div>
            <p className={`duration-200 text-nowrap font-semibold text-xs`}>
                Partner Profile
            </p>
        </div>
    )
}
