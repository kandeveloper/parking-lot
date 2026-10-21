package com.parkinglot.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;

class ParkingLotConcurrencyTest {

    @Test
    void shouldAllowParallelParkingWithoutDuplicateSlotAssignment() throws Exception {
        ParkingLot parkingLot = new ParkingLot(
                List.of(
                        new ParkingFloor("F1", List.of(
                                new ParkingSlot("A1", ParkingSlotType.CAR),
                                new ParkingSlot("A2", ParkingSlotType.CAR),
                                new ParkingSlot("A3", ParkingSlotType.CAR)
                        ))
                ),
                new HourlyPricingStrategy()
        );

        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(1);
        List<Future<ParkingTicket>> futures = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            final int index = i;
            futures.add(executor.submit(() -> {
                latch.await();
                return parkingLot.parkVehicle(new Vehicle("ABC-" + index, VehicleType.CAR));
            }));
        }

        latch.countDown();

        Set<String> slotIds = new HashSet<>();
        int successfulParkings = 0;
        for (Future<ParkingTicket> future : futures) {
            try {
                ParkingTicket ticket = future.get();
                assertNotNull(ticket);
                slotIds.add(ticket.getSlot().getSlotId());
                successfulParkings++;
            } catch (Exception ignored) {
                // expected when lot is exhausted under concurrent contention
            }
        }

        assertEquals(3, slotIds.size());
        assertEquals(3, successfulParkings);
        executor.shutdown();
    }
}
