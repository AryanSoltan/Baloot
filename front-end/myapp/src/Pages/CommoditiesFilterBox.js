import React, { useState } from "react"
import { useSearchParams } from "react-router-dom";
import './Commodities.css'


export default function CommoditiesFilterBox() {

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
        <div className="container-fluid mx-auto">
            <div className="filter-container">

                <div className="col-1 mr-auto">
                    <div className="row d-flex p-0 m-0">
                        <div className="col-1">
                            Available commodities

                            {/*<div className="custom-control custom-switch">*/}
                             <input type="checkbox" className="custom-control-input" id="available-state"/>

                            {/*</div>*/}
                        </div>


                    </div>


                </div>
                <div className="col-1 ml-auto">
                    <form>

                        <div className="form-row">
                            <div className="col-lg-4 col-sm-12 col-md-12 p-2">
                                Sort by:
                            {/*</div>*/}
                            {/*<div className="col-lg-4 col-sm-6 col-md-4">*/}
                                <button type="button" className="btn button-type2">name</button>
                            {/*</div>*/}
                            {/*<div className="col-lg-4 col-sm-6 col-md-4">*/}
                                <button type="button" className="btn button-type2">price</button>
                            </div>


                        </div>
                    </form>

                </div>


            </div>
        </div>




        </>
    );
}
