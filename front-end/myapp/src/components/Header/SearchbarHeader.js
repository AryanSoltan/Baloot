import React, { useState } from "react"
import { useSearchParams } from "react-router-dom";
import './Navbar.css'
export default function SearchbarHeader() {
//todo not complete
    const [filterBy, setFilterBy] = useState('name');
    const [searchValue, setSearchValue] = useState('');

    const [searchParams, setSearchParams] = useSearchParams();

    const [open, setOpen] = React.useState(false);

    const handleOpen = () => {
        setOpen(!open);
    };

    const setFilter = (filterType) => {
        // do something
        setOpen(false);
        setFilterBy(filterType);


    };

    const handleMenuTwo = () => {
        // do something
        setOpen(false);
    };
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
            <div className="searchbar-container">

            <form


                onSubmit={handleSearchSubmit}
                className="search-bar search-box"
            >
                <div className="dropdown">
                    <button onClick={handleOpen}>name</button>
                    {open ? (
                        <ul className="menu">
                            <li className="menu-item">
                                <a onClick={() => { setFilter('name'); }}>name</a>
                            </li>
                            <li className="menu-item">
                                <a onClick={() => { setFilter('provider'); }}>provider</a>
                            </li>
                            <li className="menu-item">
                                <a onClick={() => { setFilter('category'); }}>category</a>
                            </li>

                        </ul>
                    ) : null}
                </div>

                <input
                    type="text"
                    className="form-control" name="x" placeholder="search your product..."
                    value={searchValue}
                    onChange={e => { setSearchValue(e.target.value); }}
                />
                <button className="btn search-icon" type="submit"></button>
            </form>
            </div>

        </>
    );
}
