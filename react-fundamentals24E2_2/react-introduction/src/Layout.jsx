import React from "react";
import { Outlet } from "react-router";

export default function Layout(props) {
    return (
        <div>
            <div>Here it goes the layout</div>
            <Outlet />
        </div>
    );
}
