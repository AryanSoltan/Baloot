import axios from 'axios';
import { useEffect, useState } from 'react';
import './Rating.css'
import RateStar from './RateStar';

export default function RateMovie(props) {

    const {updateRate, userId, commodityId, userRate} = props;

    const [stars, setStars] = useState(Array(10).fill(false));
    const[rate, setRate] = useState(0)

    const handleRate = async rate => {
        try {
            console.log('in handle rate');
            console.log(rate);

            const data = {rate}
            const response = await axios.post('/commodities/'+ commodityId +'/'+userId+ '/rate/', data);

            // if(response.data.status){
            console.log("response.data.content");
            console.log(response.data.content);
                updateRate(response.data.content);
                handleHover(rate)
            // }

        }catch(e) {
            console.log(e);
        }
    }


    const handleHover = position => {
        stars.map((star, index) => {
            if(index + 1 <= userRate){
                stars[index] = true;
            }
            else if(index + 1 <= position){
                stars[index] = true;
            }
            else{
                stars[index] = false;
            }
        })
        setStars(stars.slice());

    }

    return (
        <>
            <div className="rate-stars-container">
                {
                    stars.map((star, index) => (
                        <RateStar
                            key={index}
                            starActive={star}
                            onClickHandler={
                                function (rate) {
                                    return function () {
                                        handleHover(rate);
                                        setRate(rate);
                                    }
                                }(index + 1)
                            }
                        />
                    ))
                }
                <button className="btn button-type1" onClick={() => {
                    handleRate(rate);}} >Submit</button>
            </div>
        </>
    )
}