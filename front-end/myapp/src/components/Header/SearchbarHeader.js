import { useState } from "react"
import { useSearchParams } from "react-router-dom";

export default function SearchbarHeader() {
//todo not complete
    const [filterBy, setFilterBy] = useState('name');
    const [searchValue, setSearchValue] = useState('');

    const [searchParams, setSearchParams] = useSearchParams();
    // const searchParam =

    const handleSearchSubmit = e => {
        e.preventDefault();
        setSearchParams({
            filterBy,
            searchValue
        })
    }


    return (
        <>

            <form


                onSubmit={handleSearchSubmit}
                className="search-bar search-box"
            >

                <div class="dropdown">
                    <button className="dropbtn">n</button>
                    <div className="dropdown-content ">
                        <button  className="navbar-button" onClick={() => { setFilterBy('name'); }}>name</button>

                        <button  className="navbar-button" onClick={() => { setFilterBy('category'); }}>category</button>
                        <button  className="navbar-button" onClick={() => { setFilterBy('category'); }}>provider</button>

                    </div>
                </div>

                <input
                    type="text"
                    className="form-control" name="x" placeholder="search your product..."
                    value={searchValue}
                    onChange={e => { setSearchValue(e.target.value); }}
                />
                <button className="btn search-icon" type="submit"></button>
            </form>

        </>
    );
}
