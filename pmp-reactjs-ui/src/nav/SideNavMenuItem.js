
export const SideNavMenuItem = ({ title, id, isExpanded, activeIcon }) => {
    return (
        <div className="flex items-center gap-x-2 mt-2 font-inter">
            <div className={`h-6 pl-1 w-1 ${activeIcon === id ? 'bg-tory-blue' : null} rounded-e-md`}></div>
            <div className="h-10 p-3 rounded-md shadow-md">
                {id === 'home' &&
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="18" height="19.699"
                        viewBox="0 0 18 19.699">
                        <path id="home_FILL0_wght300_GRAD0_opsz24"
                            d="M181.8-797.818h4.015v-6.929h6.369v6.929H196.2v-10.494l-7.2-5.269-7.2,5.269Zm-1.8,1.749v-13.118l9-6.581,9,6.581v13.118h-7.615V-803h-2.769v6.929ZM189-805.7Z"
                            transform="translate(-180.001 815.767)"
                            fill={activeIcon === id ? "#1447b2" : "#7a7e82"}
                        />
                    </svg>}
                {id === 'partnerTypeRequest' &&
                    <svg xmlns="http://www.w3.org/2000/svg"
                        width="19.475" height="20.475" viewBox="0 0 20.475 20.475">
                        <path
                            id="library_books_FILL0_wght200_GRAD0_opsz24"
                            d="M147.637-833.564h6.549v-3.142h-6.549Zm0,6.31h8.18v-1.175h-8.18Zm0-3.033h8.18v-1.175h-8.18Zm-2.214,6.736a1.838,1.838,0,0,1-1.355-.543,1.839,1.839,0,0,1-.543-1.355V-838.1a1.838,1.838,0,0,1,.543-1.355,1.838,1.838,0,0,1,1.355-.543h12.654a1.838,1.838,0,0,1,1.355.543,1.838,1.838,0,0,1,.543,1.355v12.654a1.838,1.838,0,0,1-.543,1.355,1.838,1.838,0,0,1-1.355.543Zm0-1.175h12.654a.691.691,0,0,0,.5-.226.691.691,0,0,0,.226-.5V-838.1a.691.691,0,0,0-.226-.5.691.691,0,0,0-.5-.226H145.423a.691.691,0,0,0-.5.226.691.691,0,0,0-.226.5v12.654a.691.691,0,0,0,.226.5A.691.691,0,0,0,145.423-824.725Zm-3.525,4.7a1.838,1.838,0,0,1-1.355-.543,1.838,1.838,0,0,1-.543-1.355v-13.829h1.175v13.829a.691.691,0,0,0,.226.5.691.691,0,0,0,.5.226h13.829v1.175Zm2.8-18.8v0Z"
                            transform="translate(-139.75 840.25)"
                            fill={activeIcon === id ? "#1447b2" : "#7a7e82"} stroke={activeIcon === id ? "#1447b2" : "#7a7e82"} stroke-width="0.5" />
                    </svg>
                }
                {id === 'organisationUsers' &&
                    <svg xmlns="http://www.w3.org/2000/svg"
                        width="23.476" height="15.543" viewBox="0 0 25.476 15.543">
                        <path id="groups_24dp_FILL0_wght200_GRAD0_opsz24"
                            d="M58.3-599.847V-601.8a3.266,3.266,0,0,1,.609-1.917,5.026,5.026,0,0,1,1.8-1.5,10.061,10.061,0,0,1,2.767-.95,17.753,17.753,0,0,1,3.487-.317,17.962,17.962,0,0,1,3.524.317,10.061,10.061,0,0,1,2.767.95,4.98,4.98,0,0,1,1.791,1.5,3.286,3.286,0,0,1,.6,1.917v1.951Zm18.907,0v-2.075a6.493,6.493,0,0,0-.182-1.564,5.858,5.858,0,0,0-.545-1.4q.543-.087.976-.125t.819-.038A6.238,6.238,0,0,1,82.086-604a3.076,3.076,0,0,1,1.691,2.657v1.5Zm-17.122-1.734H73.871v-.367q-.1-1.234-2.023-2.018a13.014,13.014,0,0,0-4.875-.784,13.014,13.014,0,0,0-4.875.784q-1.923.783-2.013,2.018Zm18.19-5.1a2.37,2.37,0,0,1-1.733-.735,2.359,2.359,0,0,1-.735-1.723,2.323,2.323,0,0,1,.735-1.731,2.414,2.414,0,0,1,1.739-.714,2.377,2.377,0,0,1,1.748.714,2.346,2.346,0,0,1,.714,1.721,2.4,2.4,0,0,1-.709,1.743A2.363,2.363,0,0,1,78.275-606.685Zm-11.29-1.1a3.689,3.689,0,0,1-2.7-1.109,3.652,3.652,0,0,1-1.117-2.693,3.652,3.652,0,0,1,1.109-2.709,3.694,3.694,0,0,1,2.694-1.094,3.684,3.684,0,0,1,2.709,1.09,3.662,3.662,0,0,1,1.093,2.7,3.722,3.722,0,0,1-1.09,2.7A3.621,3.621,0,0,1,66.985-607.785Zm0-1.734a1.95,1.95,0,0,0,1.453-.615,2.034,2.034,0,0,0,.6-1.469,1.985,1.985,0,0,0-.594-1.453,1.993,1.993,0,0,0-1.473-.6,2.02,2.02,0,0,0-1.453.595,1.966,1.966,0,0,0-.615,1.473,1.987,1.987,0,0,0,.615,1.453,2,2,0,0,0,1.469.615Zm-.007,8.507,0,0M66.973-611.587Z"
                            transform="translate(-58.301 615.39)"
                            fill={activeIcon === id ? "#1447b2" : "#7a7e82"} />
                    </svg>
                }
                {id === 'partnerCertificate' &&
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="16.277" height="20.784" viewBox="0 0 16.277 20.784">
                        <path id="upload_file_FILL0_wght200_GRAD0_opsz24"
                            d="M207.325-823.356h1.127v-5.29l2.367,2.367.8-.8-3.727-3.727-3.727,3.727.8.8,2.36-2.36Zm-5.5,3.641a1.763,1.763,0,0,1-1.3-.521,1.763,1.763,0,0,1-.521-1.3V-838.18a1.763,1.763,0,0,1,.521-1.3,1.763,1.763,0,0,1,1.3-.521h8.885l5.071,5.071v13.393a1.763,1.763,0,0,1-.521,1.3,1.763,1.763,0,0,1-1.3.521Zm8.322-14.65v-4.508H201.82a.663.663,0,0,0-.477.217.663.663,0,0,0-.217.477v16.644a.663.663,0,0,0,.217.477.663.663,0,0,0,.477.217h12.136a.663.663,0,0,0,.477-.217.663.663,0,0,0,.217-.477v-12.829Zm-9.015-4.508v0Z"
                            transform="translate(-199.75 840.25)"
                            fill={activeIcon === id ? "#1447b2" : "#7a7e82"} stroke={activeIcon === id ? "#1447b2" : "#7a7e82"} stroke-width="0.5" />
                    </svg>
                }
                {id === 'policies' &&
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="19.897" height="19.897" viewBox="0 0 19.897 19.897">
                        <path
                            id="library_books_FILL0_wght200_GRAD0_opsz24"
                            d="M147.417-833.75h6.359V-836.8h-6.359Zm0,6.128h7.943v-1.141h-7.943Zm0-2.945h7.943v-1.141h-7.943Zm-2.15,6.542a1.785,1.785,0,0,1-1.315-.528,1.785,1.785,0,0,1-.528-1.315v-12.288a1.785,1.785,0,0,1,.528-1.315,1.785,1.785,0,0,1,1.315-.528h12.288a1.785,1.785,0,0,1,1.315.528,1.785,1.785,0,0,1,.528,1.315v12.288a1.785,1.785,0,0,1-.528,1.315,1.785,1.785,0,0,1-1.315.528Zm0-1.141h12.288a.671.671,0,0,0,.483-.219.671.671,0,0,0,.219-.483v-12.288a.671.671,0,0,0-.219-.483.671.671,0,0,0-.483-.219H145.266a.671.671,0,0,0-.483.219.671.671,0,0,0-.219.483v12.288a.671.671,0,0,0,.219.483A.671.671,0,0,0,145.266-825.167Zm-3.423,4.564a1.785,1.785,0,0,1-1.315-.528,1.785,1.785,0,0,1-.528-1.315v-13.429h1.141v13.429a.671.671,0,0,0,.219.483.671.671,0,0,0,.483.219h13.429v1.141Zm2.721-18.256v0Z"
                            transform="translate(-139.75 840.25)"
                            fill={activeIcon === id ? "#1447b2" : "#7a7e82"} stroke={activeIcon === id ? "#1447b2" : "#7a7e82"} stroke-width="0.5" />
                    </svg>
                }
                {id === 'authenticationServices' &&
                    <svg 
                    xmlns="http://www.w3.org/2000/svg" 
                    width="16.289" height="20.57" viewBox="0 0 16.289 20.57">
                      <path id="encrypted_24dp_FILL0_wght300_GRAD0_opsz24" 
                      d="M186.777-845.2h2.735l-.614-3.429a1.845,1.845,0,0,0,.824-.688,1.838,1.838,0,0,0,.312-1.041,1.82,1.82,0,0,0-.554-1.335,1.82,1.82,0,0,0-1.335-.554,1.82,1.82,0,0,0-1.335.554,1.82,1.82,0,0,0-.554,1.335,1.838,1.838,0,0,0,.312,1.041,1.845,1.845,0,0,0,.824.688Zm1.368,7.309a10.489,10.489,0,0,1-5.834-4.147A11.848,11.848,0,0,1,180-849.163v-6.248l8.145-3.049,8.144,3.049v6.248a11.848,11.848,0,0,1-2.311,7.125A10.489,10.489,0,0,1,188.145-837.89Zm0-1.717a8.837,8.837,0,0,0,4.669-3.584,10.314,10.314,0,0,0,1.846-5.973v-5.131l-6.516-2.433-6.516,2.433v5.131a10.314,10.314,0,0,0,1.846,5.973A8.837,8.837,0,0,0,188.145-839.607ZM188.145-848.175Z" 
                      transform="translate(-180 858.46)" 
                      fill={activeIcon === id ? "#1447b2" : "#7a7e82"}/>
                    </svg>}
            </div>
            {isExpanded &&
                <p className={`duration-200 ${!isExpanded && 'scale-0'} text-nowrap font-medium text-xs`}>
                    {title}
                </p>}
        </div>
    )
}
