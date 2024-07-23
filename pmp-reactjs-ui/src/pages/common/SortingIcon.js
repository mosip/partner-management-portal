function SortingIcon ({ headerId, sortDescOrder, sortAscOrder, order, activeSortDesc, activeSortAsc}) {
    return (
        <div>
            <svg className="cursor-pointer mx-2 mb-0.5" onClick={() => sortDescOrder(headerId)} alt="Descending" tabIndex="0" onKeyPress={(e)=>sortDescOrder(headerId)}
                xmlns="http://www.w3.org/2000/svg"
                width="8" height="8" viewBox="0 0 7 6">
                <path id="Polygon_3" data-name="Polygon 3" d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                fill={`${(activeSortDesc === headerId && order === "DESC") ? "#1447b2" : "#969696"}`} />
            </svg>
            <svg className="cursor-pointer mx-2" onClick={() => sortAscOrder(headerId)} alt="Ascending" tabIndex="0" onKeyPress={(e)=>sortAscOrder(headerId)}
                xmlns="http://www.w3.org/2000/svg"
                width="8" height="8" viewBox="0 0 7 6">
                <path id="Polygon_4" data-name="Polygon 4" d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                transform="translate(7 6) rotate(180)" fill={`${(activeSortAsc === headerId && order === "ASC") ? "#1447b2" : "#969696"}`} />
            </svg>
        </div>
    )
}

export default SortingIcon;