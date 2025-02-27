import { describe, it, expect, beforeEach, vi } from "vitest";
import { shallowMount } from "@vue/test-utils";
import SettingView from "@/views/SettingView.vue";

vi.stubGlobal("alert", vi.fn());

describe("SettingView.vue", () => {
    let wrapper:any;

    beforeEach(() => {
        wrapper = shallowMount(SettingView);
    });

    it("Display component", () => {
        expect(wrapper.exists()).toBe(true);
        expect(wrapper.find("h6").text()).toContain("Delivery time");
    });

    it("Init default value", async () => {
        expect(wrapper.vm.deliveryDelayInput).toBe(0);
    });

    it("Modify input value on type", async () => {
        const input = wrapper.find("input[type='number']");
        await input.setValue(5);
        expect(wrapper.vm.deliveryDelayInput).toBe(5);
    });

    it("call patch delivery when click", async () => {
        const mockPatchDelivery = vi.spyOn(wrapper.vm, "patch_Delivery");
        await wrapper.find("button").trigger("click");
        expect(mockPatchDelivery).toHaveBeenCalled();
    });
});
