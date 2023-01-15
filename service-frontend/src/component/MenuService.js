import React from 'react';
import { Tabs } from 'antd';
import {RoutePage} from "../container/RoutePage";
import NavigationPage from "../container/NavigationPage";
import TripPage from "../container/TripPage";

const onChange = (key) => {
    //setPage(key)
};

const MenuService = () => (
    <Tabs
        onChange={onChange}
        type="card"
        items={[{
                label: "Route Service",
                key: 1,
                children: <RoutePage/>
            },
            {
                label: "Navigation Service",
                key: 2,
                children: <NavigationPage/>
            },
            {
                label: "Trip Service",
                key: 3,
                children: <TripPage/>
            },
        ]}
    />
);

export default MenuService;