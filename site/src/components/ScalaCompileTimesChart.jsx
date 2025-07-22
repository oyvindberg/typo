// all from chatgpt, don't judge this :D

import React, { useState, useEffect } from 'react';
import Chart from 'chart.js/auto';

const ScalaCompileTimesChart = ({id, children: csvData}) => {

    const [chartInstance, setChartInstance] = useState(null);
    const [selectedView, setSelectedView] = useState('min');

    useEffect(() => {
        const rows = csvData.trim().split('\n').map(row => row.split(','));
        const libraries = [...new Set(rows.slice(1).map(row => row[0]))];
        const scalaVersions = [...new Set(rows.slice(1).map(row => row[1]))];
        const inlinedImplicits = [...new Set(rows.slice(1).map(row => row[2]))];

        const generateColor = (scalaVersion, inlined) => {
            const baseHue =
                scalaVersion === 'scala213'
                    ? 200
                    : scalaVersion === 'scala3'
                        ? 40
                        : scalaVersion === 'scala212'
                            ? 120
                            : 0; // Adjust hues for Scala versions

            const lightness = inlined === 'true' ? 70 : 50; // Brightness

            return `hsl(${baseHue}, 70%, ${lightness}%)`;
        };

        // Clean up any existing chart before creating a new one
        let existingChart = Chart.getChart(id);
        if (existingChart) {
            existingChart.destroy();
        }

        const chartData = {
            labels: libraries,
            datasets: scalaVersions.flatMap(scalaVersion => {
                return inlinedImplicits.map(inlined => {
                    const data = libraries.map(library => {
                        const rowData = rows.find(
                            row =>
                                row[0] === library &&
                                row[1] === scalaVersion &&
                                row[2] === inlined
                        );
                        return rowData
                            ? selectedView === 'avg'
                                ? parseFloat(rowData[3])
                                : parseFloat(rowData[4])
                            : 0;
                    });
                    return {
                        label: (inlinedImplicits.length === 1) ? scalaVersion : `${scalaVersion} - Inlined: ${inlined}`,
                        backgroundColor: generateColor(scalaVersion, inlined),
                        data: data,
                    };
                });
            }).flat(),
        };

        const ctx = document.getElementById(id).getContext('2d');
        const newChartInstance = new Chart(ctx, {
            type: 'bar',
            data: chartData,
            options: {
                responsive: true,
                maintainAspectRatio: false,
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text:
                        selectedView === 'avg'
                            ? 'Average Compile Times (Seconds)'
                            : 'Minimum Compile Times (Seconds)',
                },
                scales: {
                    y:  {
                        stacked: false,
                        ticks: {
                            beginAtZero: true,
                            // callback: value => value + 's',
                        },
                        title: {
                            display: true,
                            text: 'Milliseconds',
                        },
                    },
                },
            },
        });
        setChartInstance(newChartInstance);

        // Clean up on unmount
        return () => {
            if (newChartInstance) {
                newChartInstance.destroy();
            }
        };
    }, [selectedView, csvData, id]);

    const handleViewChange = event => {
        setSelectedView(event.target.value);
    };

    return (
        <div style={{ width: '80%', margin: 'auto' }}>
            <select id="toggleView" onChange={handleViewChange} value={selectedView}>
                <option value="min">Minimum Compile Time</option>
                <option value="avg">Average Compile Time</option>
            </select>
            <div className="chart-container">
                <canvas id={id} width="800" height="400"></canvas>
            </div>
        </div>
    );
};

export default ScalaCompileTimesChart;