GC Airhorn
===========
Android library for playing AirHorn sound ([YouTube](https://www.youtube.com/watch?v=2Tt04ZSlbZ0)) when **GC_FOR_ALLOC** event occurs.
![AirHorn](./airhorn.png)

Usage
------
Obtain `GcAirHorn` instance via `GcAirHorn.getInstance()` and use `startListening()` method to start listening 
for **GC_FOR_ALLOC** event. After all your things are done, use `stopListening()` to stop listening for **GC_FOR_ALLOC** event.
 
License
--------
    Copyright (c) 2015
    
    Licensed under the Apache License, Version 2.0 (the "License" you may
    not use this file except in compliance with the License. You may obtain
    a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
