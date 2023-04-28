import { useEffect, useState } from "react";

import { Icon } from '@iconify/react';


export default function RateStar(props) {
    const { onClickHandler,  starActive } = props;

    return (
        <div
            onClick={onClickHandler}
            className="rate-star"

        >
            <Icon
                icon="codicon:star-full"
                style={{ color: (starActive ? "yellow" : "grey") }}
                className="star-icon "
            />
        </div>
    )
}