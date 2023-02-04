import {Button, Col, Divider, InputNumber, Layout, notification, Row, Space} from "antd";
import TableRoute from "../component/tables/TableRoute";
import FormTicket from "../component/forms/FormTicket";
import {useState} from "react";
import api from "../service/tripInstance";
import {setData, setIsModalOpen, setIsTicketModalOpen, setMessage} from "../store/action/pageAction";
import {useDispatch, useSelector} from "react-redux";
import {store} from "../store/store";
import TableTicket from "../component/tables/TableTicket";

const TripPage = () => {
    const page = useSelector(store => store.page);
    const dispatch = useDispatch();
    const [notificationApi, contextHolder] = notification.useNotification();

    const [showId, setShowId] = useState(1);
    const [deletePassengerId, setDeletePassengerId] = useState(1);
    const [deleteId, setDeleteId] = useState(1);

    const handleShow = () => {
        api.get('/tickets/' + showId)
            .then(res => {
                console.log(res);
                if (res.status === 200 && res.data.code === undefined) {
                    dispatch(setMessage("Tickets for passenger with ID = " + showId + " are in the table!"));
                    dispatch(setData(res.data));
                    openNotification('success');
                } else {
                    dispatch(setMessage("Something go wrong"));
                    openNotification('error');
                }
            })
            .catch(err => {
                dispatch(setMessage(err.response.data.error.message));
                openNotification('error');
            })
    }

    const handleDelete = () => {
        api.delete('/tickets/' + deleteId + "/" + deletePassengerId)
            .then(res => {
                console.log(res);
                if (res.status === 200 && res.data.code === undefined) {
                    dispatch(setMessage("Ticket with ID = " + deleteId + " was successfully deleted!"));
                    dispatch(setData([])); //todo: refresh if passengerId matches
                    openNotification('success');
                } else {
                    if (res.data.code === 404) {
                        dispatch(setMessage(res.data.message));
                    } else {
                        dispatch(setMessage("Something go wrong"));
                    }
                    openNotification('error');
                }
            })
            .catch(err => {
                dispatch(setMessage(err.response.data.error.message));
                openNotification('error');
            })
    }

    const handleAdd = () => {
        dispatch(setIsTicketModalOpen(true));
    }

    const openNotification = (type) => {
        notificationApi[type]({
            message: 'Notification',
            description: store.getState().page.msg,
        });
    };
    
    return (
        <Layout className='page-content'>
            {contextHolder}
            <Row>
                <Col span={12} style={{padding: '2.9% 0'}}>
                    <Row align='center'>
                        <Space direction='horizontal'>
                            <p>Passenger ID:</p>
                            <InputNumber min={1} defaultValue={1} value={showId} onChange={setShowId}/>
                            <Button type='primary' onClick={handleShow}>Show tickets</Button>
                        </Space>
                    </Row>
                    <Divider/>
                    <Row align='center'>
                        <Col span={8}>
                            <Row align='center'>
                                <Space direction='horizontal'>
                                    <p>Passenger ID:</p>
                                    <InputNumber min={1} defaultValue={1} value={deletePassengerId} onChange={setDeletePassengerId}/>
                                </Space>
                            </Row>
                            <Row align='end'>
                                <Space direction='horizontal'>
                                    <p>ID:</p>
                                    <InputNumber min={1} defaultValue={1} value={deleteId} onChange={setDeleteId}/>
                                </Space>
                            </Row>
                        </Col>
                        <Col span={6}>
                            <Row align='end' style={{padding: '20% 0'}}>
                                <Button type='primary' onClick={handleDelete}>Delete ticket</Button>
                            </Row>
                        </Col>
                    </Row>
                </Col>
                <Col span={10}>
                    <Row align='center' style={{padding: '23% 0'}}>
                        <Space direction='vertical' align='center'>
                            <Button type='primary' onClick={handleAdd}>Buy ticket</Button>
                        </Space>
                        <FormTicket/>
                    </Row>
                </Col>
            </Row>
            <Row>
                <TableTicket/>
            </Row>
        </Layout>
)};
export default TripPage;