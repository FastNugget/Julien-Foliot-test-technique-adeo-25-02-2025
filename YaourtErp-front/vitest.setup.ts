import { vi } from "vitest";

vi.mock("chart.js", () => ({
    Chart: {
        register: vi.fn(), // Mock global de Chart.js
    },
}));