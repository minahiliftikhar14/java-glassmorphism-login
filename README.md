# Modern Glassmorphism Login UI (Java Swing)

A sleek and modern Desktop Login Interface built entirely in **Java Swing** and **AWT**. This project showcases custom component rendering using Java 2D graphics (`Graphics2D`) to achieve a modern **Glassmorphism / Dark UI design** without relying on third-party UI libraries or frameworks.

---

## Key Features

- **Custom Glassmorphism UI**: Uses translucent dark background cards, custom gradients, and soft borders.
- **Custom Java 2D Rendering**: Overridden `paintComponent` methods in components like buttons, text fields, and panels for smooth rounded corners and hover effects.
- **Undecorated Draggable Window**: Custom dark top bar supporting mouse-drag functionality to move the frame.
- **Interactive Controls**:
  - Show / Hide password toggle.
  - Custom Close (`✕`) button with smooth hover animation.
  - Field validation and feedback dialogs (`JOptionPane`).
  - Clear fields / Reset option.
- **Zero External Dependencies**: Built entirely using standard Java Swing and AWT libraries (`javax.swing.*`, `java.awt.*`).

---

## Tech Stack & Concepts

- **Language**: Java
- **GUI Toolkit**: Java Swing & AWT
- **Core Concepts**:
  - Object-Oriented Programming (OOP) & Inheritance (Extending Swing components).
  - Custom Graphic Rendering (`Graphics2D`, `GradientPaint`, `BasicStroke`).
  - Event Handling (`ActionListener`, `MouseAdapter`, `MouseMotionAdapter`).

---

## How to Run

### Prerequisites
- **Java Development Kit (JDK 8 or higher)** installed on your machine.

### Execution Steps

1. **Clone the repository**:
   ```bash
   git clone [https://github.com/YOUR_USERNAME/java-glassmorphism-login.git](https://github.com/miahiliftikhar14/java-glassmorphism-login.git)
   cd java-glassmorphism-login
   javac -d bin src/LoginFrame.java
   java -cp bin LoginFrame
   .
