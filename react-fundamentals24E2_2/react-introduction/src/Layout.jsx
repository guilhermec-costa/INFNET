import React from "react";
import { Outlet } from "react-router";
import { RandomContextProvider } from "./contexts/RandomContext";

export default function Layout(props) {
    return (
        <div>
            <div>Here it goes the layout</div>
            <Outlet />
        </div>
    );
}
