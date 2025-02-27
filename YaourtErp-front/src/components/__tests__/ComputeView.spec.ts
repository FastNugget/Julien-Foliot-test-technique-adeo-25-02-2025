import { describe, it, expect, beforeEach, afterEach, vi } from "vitest";
import { shallowMount } from "@vue/test-utils";
import ComputeView from "@/views/ComputeView.vue";
import { YaourtComputeDtoRES } from "@/model/yaourt/YaourtComputeDtoRES";
import { DailyConsumption } from "@/model/yaourt/DailyConsumption";

vi.mock("axios", () => ({
    create: vi.fn(() => ({
        get: vi.fn(() => Promise.resolve({ data: {} })), // Simule une réponse GET
        put: vi.fn(() => Promise.resolve({ data: {} })), // Simule une réponse PUT
    })),
}));

describe("ComputeView.vue", () => {

    vi.mock("chart.js", async () => {
        const actual = await vi.importActual<typeof import("chart.js")>("chart.js");
        return {
            ...actual, // Garde les vraies fonctions de Chart.js
            Chart: {
                register: vi.fn(), // Mock `Chart.register()`
            },
            registerables: [], // Mock `registerables`
            TimeScale: {}, // 🔹 Mock `TimeScale`
        };
    });

    let wrapper: any;

    beforeEach(() => {
        global.window.alert = vi.fn(); // Mock `window.alert`
        wrapper = shallowMount(ComputeView);
    });

    afterEach(() => {

    });

    it("Max consumption", async () => {
        wrapper.vm.yaourtDtoRES = new YaourtComputeDtoRES(100, [
            new DailyConsumption("2025-02-10", 3),
            new DailyConsumption("2025-02-11", 5),
            new DailyConsumption("2025-02-12", 8),
            new DailyConsumption("2025-02-13", 6),
            new DailyConsumption("2025-02-14", 4),
        ]);

        const maxConsumption = wrapper.vm.calculateAverageConsumption();
        expect(maxConsumption).toBe(8);
    });

    it("updatye quantityToBuy", async () => {
        wrapper.vm.yaourtDtoRES = new YaourtComputeDtoRES(100, [
            new DailyConsumption("2025-02-10", 3),
            new DailyConsumption("2025-02-11", 5),
        ]);
        wrapper.vm.stockDtoRes = { quantity: 20, quantityMultiple: 6 };
        await wrapper.vm.setQuantityToBuy();

        expect(wrapper.vm.quantityToBuy).toBe(80); // 100 - 20 = 80
    });


    it("convert startDate in ISO", async () => {
        wrapper.vm.startDate = "2025-01-06";
        await wrapper.vm.setQuantityToBuy();
        expect(wrapper.vm.isoDate).toBe(new Date("2025-01-06").toISOString());
    });

    it("update chartData after computeYaourt()", async () => {
        wrapper.vm.yaourtDtoRES = new YaourtComputeDtoRES(100, [
            new DailyConsumption("2025-02-10", 3),
            new DailyConsumption("2025-02-11", 5),
        ]);
        await wrapper.vm.$nextTick();

        expect(wrapper.vm.chartData.labels.length).toBe(2);
        expect(wrapper.vm.chartData.datasets[0].data.length).toBe(2);
    });

});