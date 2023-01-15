import {Button, Col, Divider, InputNumber, Layout, Row, Space} from "antd";
import TableRoute from "../component/TableRoute";
import FormTicket from "../component/forms/FormTicket";
import {useState} from "react";

const TripPage = () => {
    const [showId, setShowId] = useState(1);
    const [deletePassengerId, setDeletePassengerId] = useState(1);
    const [deleteId, setDeleteId] = useState(1);
    
    return (
        <Layout className='page-content'>
            <Row>
                <Col span={12} style={{padding: '2.9% 0'}}>
                    <Row align='center'>
                        <Space direction='horizontal'>
                            <p>Passenger ID:</p>
                            <InputNumber min={1} defaultValue={1} value={showId} onChange={setShowId}/>
                            <Button type='primary'>Show tickets</Button>
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
                                <Button type='primary'>Delete ticket</Button>
                            </Row>
                        </Col>
                    </Row>
                </Col>
                <Col span={10}>
                    <Row align='center' style={{padding: '23% 0'}}>
                        <Space direction='vertical' align='center'>
                            <Button type='primary'>Buy ticket</Button>
                        </Space>
                        <FormTicket/>
                    </Row>
                </Col>
            </Row>
            <Row>
                <TableRoute/>
            </Row>
        </Layout>
)};
export default TripPage;