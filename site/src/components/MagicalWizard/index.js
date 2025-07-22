import React from 'react';
import styles from './styles.module.css';

const MagicalWizard = ({ size = 'medium', spell = 'Type Safety' }) => {
  const sizeClass = {
    small: styles.wizardSmall,
    medium: styles.wizardMedium,
    large: styles.wizardLarge
  };

  return (
    <div className={`${styles.wizard} ${sizeClass[size]}`}>
      <div className={styles.wizardContainer}>
        {/* Wizard Hat */}
        <div className={styles.wizardHat}>
          <div className={styles.hatStar}>✨</div>
        </div>
        
        {/* Wizard Head */}
        <div className={styles.wizardHead}>
          <div className={styles.wizardEyes}>
            <div className={styles.eye}></div>
            <div className={styles.eye}></div>
          </div>
          <div className={styles.wizardSmile}></div>
        </div>
        
        {/* Wizard Body */}
        <div className={styles.wizardBody}></div>
        
        {/* Wizard Beard */}
        <div className={styles.wizardBeard}></div>
        
        {/* Magic Staff */}
        <div className={styles.magicStaff}>
          <div className={styles.staffCrystal}></div>
          <div className={styles.staffHandle}></div>
        </div>
        
        {/* Magic Sparkles */}
        <div className={styles.sparkles}>
          <div className={`${styles.sparkle} ${styles.sparkle1}`}>✨</div>
          <div className={`${styles.sparkle} ${styles.sparkle2}`}>💫</div>
          <div className={`${styles.sparkle} ${styles.sparkle3}`}>⭐</div>
        </div>
        
        {/* Speech Bubble */}
        <div className={styles.speechBubble}>
          <div className={styles.speechText}>{spell}</div>
        </div>
      </div>
    </div>
  );
};

export default MagicalWizard;