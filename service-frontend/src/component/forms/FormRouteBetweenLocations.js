import {Button, Cascader, Form, InputNumber, notification, Row, Space} from "antd";
import api from "../../service/axiosRemoteInstance";
import {addQueryParams} from "../../utils/ModalType";
import {setData, setInstance, setMessage} from "../../store/action/pageAction";
import {useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {store} from "../../store/store";

const FormRouteBetweenLocations = () => {
    const [form] = Form.useForm();
    const page = useSelector(store => store.page);
    const dispatch = useDispatch();

    const [fromId, setFromId] = useState(1);
    const [toId, setToId] = useState(1);
    const [orderBy, setOrderBy] = useState("id");

    const [notificationApi, contextHolder] = notification.useNotification();

    const handleFind = () => {
        api.get('/navigator/routes/' + fromId + "/" + toId + "/" + orderBy+ addQueryParams({paramName: "page", paramValue: 1}, {paramName: "limit", paramValue: 100})) //todo: добавить параметры в запрос
            .then(
                res => {
                    if (res.status === 200 && res.data.code === undefined) {
                        dispatch(setData(res.data));
                    } else {
                        dispatch(setMessage(res.data.message));
                        if (res.data.code === 404) {
                            openNotification('warning');
                        } else {
                            openNotification('error');
                        }
                    }
                }
            )
            .catch(err => {
                dispatch(setMessage(err.message));
                openNotification('error');
            })
    }

    const openNotification = (type) => {
        notificationApi[type]({
            message: 'Notification',
            description: store.getState().page.msg,
        });
    };

    return (
        <Form form={form}>
            {contextHolder}
            <Row align='center'>
                <Space>
                    <p>Find all routes between locations with ID</p>
                    <Form.Item name='fromId' required>
                        <InputNumber min={1} value={fromId} onChange={e => setFromId(e)}/>
                    </Form.Item>
                    <p>and</p>
                    <Form.Item name='toId' required>
                        <InputNumber min={1} value={toId} onChange={e => setToId(e)}/>
                    </Form.Item>
                </Space>
                <Space>
                    <p>sorted by</p>
                    <Form.Item name='orderBy'>
                        <Cascader
                            value={orderBy} onChange={e => setOrderBy(e)}
                            style={{width: '15em'}}
                            options={[
                                {
                                    value: 'id',
                                    label: 'ID',
                                },
                                {
                                    value: 'name',
                                    label: 'Name',
                                },
                                {
                                    value: 'coordinates',
                                    label: 'Coordinates',
                                    children: [
                                        {
                                            value: 'x',
                                            label: 'Coordinate X',
                                        },
                                        {
                                            value: 'y',
                                            label: 'Coordinate Y',
                                        },
                                    ],
                                },
                                {
                                    value: 'location from',
                                    label: 'Location from',
                                    children: [
                                        {
                                            value: 'fromX',
                                            label: 'Coordinate X',
                                        },
                                        {
                                            value: 'fromY',
                                            label: 'Coordinate Y',
                                        },
                                        {
                                            value: 'fromZ',
                                            label: 'Coordinate Z',
                                        },
                                    ],
                                },
                                {
                                    value: 'location to',
                                    label: 'Location to',
                                    children: [
                                        {
                                            value: 'toX',
                                            label: 'Coordinate X',
                                        },
                                        {
                                            value: 'toY',
                                            label: 'Coordinate Y',
                                        },
                                        {
                                            value: 'toZ',
                                            label: 'Coordinate Z',
                                        },
                                        {
                                            value: 'toName',
                                            label: 'Name'
                                        },
                                    ],
                                },
                                {
                                    value: 'distance',
                                    label: 'Distance',
                                },
                            ]}
                        />
                    </Form.Item>
                    <Space/>
                    <Form.Item name='Find'>
                        <Button key='submit' type='primary' onClick={handleFind}>Find</Button>
                    </Form.Item>
                </Space>
            </Row>
        </Form>
    )
};
export default FormRouteBetweenLocations;