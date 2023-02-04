import React, {useState} from 'react';
import { Tabs } from 'antd';
import {RoutePage} from "../container/RoutePage";
import NavigationPage from "../container/NavigationPage";
import TripPage from "../container/TripPage";
import {useDispatch, useSelector} from "react-redux";
import {setTabNumber} from "../store/action/pageAction";

const MenuService = () => {
    const page = useSelector(store => store.page);
    const dispatch = useDispatch();

    const onChange = (key) => {
        dispatch(setTabNumber(key));
    };

    return (
        <Tabs
            activeKey={page.tab_number}
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
}

export default MenuService;