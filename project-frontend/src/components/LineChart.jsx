import React from "react";
import { Line } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from "chart.js";

// Register Chart.js components
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const LineChart = ({ data }) => {
    // Prepare the labels (hours) and datasets (one for each device)
    const labels = data[0]?.consumptions.map(entry => entry.hour) || []; // Assumes all devices have the same time range
    const datasets = data.map(device => ({
        label: device.deviceName,
        data: device.consumptions.map(entry => entry.consumption),
        borderColor: device.color || `#${Math.floor(Math.random() * 16777215).toString(16)}`, // Assign random color if not provided
        backgroundColor: "rgba(0,0,0,0.0)",
        tension: 0.4,
        pointRadius: 5,
    }));

    const chartData = { labels, datasets };

    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: "top",
            },
            title: {
                display: true,
                text: `Energy Consumption (kWh) by Hour for All Devices`,
            },
        },
        scales: {
            x: {
                title: {
                    display: true,
                    text: "Hour of the Day",
                },
            },
            y: {
                title: {
                    display: true,
                    text: "Energy Consumption (kWh)",
                },
                beginAtZero: true,
            },
        },
    };

    return <Line data={chartData} options={options} />;
};

export default LineChart;
