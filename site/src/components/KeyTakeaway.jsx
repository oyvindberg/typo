import React from 'react';
import {useColorMode} from '@docusaurus/theme-common';

const KeyTakeaway = ({ children:text }) => {
    const { isDarkTheme } = useColorMode();

    const containerStyle = {
        backgroundColor: isDarkTheme ? '#333' : '#f0f0f0', // Dark or light background color
        color: isDarkTheme ? '#fff' : '#000', // Text color based on mode
        padding: '20px',
        borderRadius: '8px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    };

    return (
        <div style={containerStyle}>
            <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                stroke={isDarkTheme ? '#fff' : 'currentColor'}
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
                style={{ marginRight: '10px' }}
            >
                <path d="M7 20v1a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1v-1" />
                <path d="M9 20h6" />
                <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 16h-2v-2h2v2zm0-4h-2V8h2v6z" />
            </svg>
            <span>Key takeaway: {text}</span>
        </div>
    );
};

export default KeyTakeaway;
